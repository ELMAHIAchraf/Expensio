package com.example.Expenses.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class NoImageUploadedException extends RuntimeException{

    private String message;

    public NoImageUploadedException(String message) {
        super(message);
    }
}
