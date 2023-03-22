package com.javarush.golf.filippov.todotask.controllers;


import com.javarush.golf.filippov.todotask.exception.EntityNotFoundException;
import com.javarush.golf.filippov.todotask.exception.MyRequestException;
import com.javarush.golf.filippov.todotask.model.Task;
import com.javarush.golf.filippov.todotask.model.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestTaskController {

    @Autowired
    private TaskRepository todoTaskRepository;

    @GetMapping("/tasks")
    public List<Task> list() {
        return todoTaskRepository.findAll();
    }


    @DeleteMapping("/tasks")
    public List<Task> clearTask() {
        todoTaskRepository.deleteAll();
        return todoTaskRepository.findAll();
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> add(@Valid @RequestBody Task task, BindingResult bindingResult) {
        System.out.println(task.getDescription());
        System.out.println(task.getStatus());
        System.out.println(bindingResult.getModel());

        if (bindingResult.hasErrors()) {
            throw new MyRequestException(task);
        }
        Task newTodoTask = todoTaskRepository.save(task);
        return ResponseEntity.ok(newTodoTask);
    }

    @GetMapping("/tasks/{id}")
    public Task get(@PathVariable int id) {
        return todoTaskRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<?> putTask(@Valid @RequestBody Task task, @PathVariable int id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MyRequestException(task);
        }
        task.setId(id);
        Task newTodoTask = todoTaskRepository.save(task);
        return ResponseEntity.ok(newTodoTask);
    }

    @DeleteMapping("/tasks/{id}")
    public List<Task> deleteTask(@PathVariable int id) {
        todoTaskRepository.deleteById(id);
        Iterable<Task> optionalTodoTask = todoTaskRepository.findAll();
        return new ArrayList<Task>((Collection<? extends Task>) optionalTodoTask);
    }

/*    @GetMapping("/tasks/search")
    @ResponseBody
    public List<Task> filterLst(@RequestParam Map<String, String> allParams) {
//        System.out.println(allParams + "!!!!!!");
        Iterable<Task> optionalTodoTask = todoTaskRepository.findAll();
        List<Task> todoTasks = new ArrayList<Task>((Collection<? extends Task>) optionalTodoTask);
        if (allParams.containsKey(Task.getValues().get(4)) && !allParams.get(Task.getValues().get(4)).isEmpty()) {
            LocalDateTime dateTime = null;
            dateTime = LocalDateTime.parse(allParams.get(Task.getValues().get(4)).strip());

            boolean isAfter = false;
            if (allParams.containsKey("date")) {
                isAfter = allParams.get("date").strip().equalsIgnoreCase("before");
            }
            for (int i = 0; i < todoTasks.size(); i++) {
                if (isAfter ? todoTasks.get(i).getDeadline().isBefore(dateTime) : todoTasks.get(i).getDeadline().isAfter(dateTime)) {
                    todoTasks.remove(todoTasks.get(i));
                    i--;
                }
            }
        }
        allParams.remove(Task.getValues().get(4));
        for (Map.Entry<String, String> param : allParams.entrySet()
        ) {
            if (Task.getValues().contains(param.getKey().strip())) {
                for (int i = 0; i < todoTasks.size(); i++) {
                    String value = getValue(todoTasks, param, i);
                    if (!value.toLowerCase().contains(param.getValue().strip().toLowerCase())) {
                        todoTasks.remove(todoTasks.get(i));
                        i--;
                    }
                }
            }
        }
        return todoTasks;
    }*/

    private String getValue(List<Task> todoTasks, Map.Entry<String, String> param, int i) {
        String value = "";
        if (param.getKey().strip().equalsIgnoreCase(Task.getValues().get(1))) {
            value = todoTasks.get(i).getDescription();
        } else if (param.getKey().strip().equalsIgnoreCase(Task.getValues().get(2))) {
            value = todoTasks.get(i).getStatus().toString();
        }
        return value;
    }


    @GetMapping("/tasks/search_title")
    @ResponseBody
    public List<Task> filterLst(@RequestParam(required = false) String description/*, @RequestParam(required = false) String status*/) {
        System.out.println(description+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if(description.equalsIgnoreCase("magic word")){
            return list();
        }
        if (description == null || description.isEmpty()) {
            /*if (status == null || status.isEmpty()) {
                return todoTaskRepository.findAll();
            }
            return todoTaskRepository.findByStatus(status);*/
        }
        return todoTaskRepository.findByDescription(description);

    }

    @GetMapping("/hello")
    ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }

}