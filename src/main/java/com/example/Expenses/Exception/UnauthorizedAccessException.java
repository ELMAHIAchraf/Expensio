package com.example.Expenses.Exception;

public class UnauthorizedAccessException extends  RuntimeException{
    private String message;

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
