package com.javarush.golf.filippov.todotask.exception;

import com.javarush.golf.filippov.todotask.model.Status;
import com.javarush.golf.filippov.todotask.model.Task;

public class MyRequestException extends RuntimeException {
    String message;
    Task task;

    public MyRequestException(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    public String getBadValue(Task task) {
        String description = "", status = "";

        if (task.getDescription().isEmpty()) {
            description = "Description isEmpty()";
        }
        if (!task.getStatus().toString().isEmpty()) {
            if (task.getStatus() != Status.DONE || task.getStatus() != Status.PAUSED || task.getStatus() != Status.IN_PROGRESS) {
                status = "Status isBad";
            }
        } else {
            status = "Status isEmpty";
        }
        return "error in - " + String.join(", ", description, status);
    }
}
