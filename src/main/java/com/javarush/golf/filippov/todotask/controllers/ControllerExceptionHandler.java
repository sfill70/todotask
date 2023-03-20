package com.javarush.golf.filippov.todotask.controllers;

import com.javarush.golf.filippov.todotask.exception.EntityNotFoundException;
import com.javarush.golf.filippov.todotask.exception.MyRequestException;
import com.javarush.golf.filippov.todotask.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> requestMyException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }


    @ExceptionHandler(MyRequestException.class)
    public ResponseEntity<Task> putRequestException(MyRequestException e){
        return ResponseEntity.badRequest().body(e.getTask());
    }


}
