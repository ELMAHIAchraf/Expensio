package com.example.Expenses.Exception;

public class InvalidOldPasswordException extends RuntimeException{
    private String message;

    public InvalidOldPasswordException(String message) {
        super(message);
    }
}
