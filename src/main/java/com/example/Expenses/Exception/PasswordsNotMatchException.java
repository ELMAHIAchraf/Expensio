package com.example.Expenses.Exception;

public class PasswordsNotMatchException extends RuntimeException{
    private String message;

    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
