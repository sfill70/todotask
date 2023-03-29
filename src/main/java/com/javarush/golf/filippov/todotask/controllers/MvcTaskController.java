package com.javarush.golf.filippov.todotask.controllers;

import com.javarush.golf.filippov.todotask.model.Status;
import com.javarush.golf.filippov.todotask.model.Task;
import com.javarush.golf.filippov.todotask.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MvcTaskController {

    @Autowired
    private TaskRepository todoTaskRepository;


    @GetMapping({"/", "/todotask/"})
    public String viewTaskAll(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.get() - 1, 10);
        model.addAttribute("todoTask", new Task());
        List<Task> tasks = todoTaskRepository.findAll();
        int tasksCount = tasks.size();
        int countPage = tasks.size() / 10 + 1;
        tasks = todoTaskRepository.findAll(pageable).toList();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasksCount);
        model.addAttribute("countPage", countPage);
        if (countPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, countPage).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

    @GetMapping({"/search","/todotask/search", "/*/search","*/search"})
    public String searchTaskAll(Model model, @RequestParam(required = false) String description/*, @RequestParam(required = false) String status *//*@RequestParam(required = false) Map<String, String> allParams*/) {
        List<Task> tasks;
        if (description != null) {
            tasks = todoTaskRepository.findByDescription(description);
        } else {
            tasks = todoTaskRepository.findAll();
        }
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasks.size());
        return "search";
    }

    @GetMapping({"/search_st","/todotask/search_st","/*/search_st", "*/search_st"})
    public String searchTaskStatus(Model model, @RequestParam(required = false) String status /*@RequestParam(required = false) Map<String, String> allParams*/) {
        List<Task> tasks;
        if (status != null) {
            tasks = todoTaskRepository.findByStatus(Status.valueOf(status));
        } else {
            tasks = todoTaskRepository.findAll();
        }
        System.out.println(tasks);
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksCount", tasks.size());
        return "search";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String taskFormSubmit(@Valid @ModelAttribute Task todoTask, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "заполните все поля");
            return "redirect:/";
        }
        todoTaskRepository.save(todoTask);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String clearTask(Model model) {
        todoTaskRepository.deleteAll();
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewTask(@PathVariable int id, Model model) {
        Optional<Task> todoTask = todoTaskRepository.findById(id);
        if (todoTask.isPresent()) {
            Task task = todoTask.get();
            model.addAttribute("task", task);
            model.addAttribute("view", true);
            model.addAttribute("description", task.getDescription());
            model.addAttribute("status", task.getStatus());
        } else {
            String answer = "the page does not exist";
            model.addAttribute("view", false);
            model.addAttribute("description", answer);
        }
        return "task_view";
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.GET)
    public String taskViewPut(@PathVariable int id, Model model) {
        Optional<Task> task = todoTaskRepository.findById(id);
        if (task.isPresent()) {
            Task todoTask = task.get();
            String answer = "Edit page Id=" + id;
            model.addAttribute("todoTask", todoTask);
            model.addAttribute("view", true);
            model.addAttribute("title", answer);
        } else {
            String answer = "the page does not exist";
            model.addAttribute("view", false);
            model.addAttribute("title", answer);
            return "error";
        }
        return "task_put";
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.POST)
    public String taskPut(@PathVariable int id, @Valid @ModelAttribute Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/put/{id}";
        }
        task.setId(id);
        todoTaskRepository.save(task);
        return "redirect:/{id}";
    }

//    use modal window task_view.html
    @RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
    public String deleteTask(@PathVariable int id, Model model) {
        todoTaskRepository.deleteById(id);
        return "redirect:/";
    }

//    use modal window index.html
    @RequestMapping(value = "/dlt/", method = RequestMethod.POST)
    public String delete(Model model, @RequestParam(value = "id", required = false) Optional<Integer> id) {
        todoTaskRepository.deleteById(id.get());
        return "redirect:/";
    }
}