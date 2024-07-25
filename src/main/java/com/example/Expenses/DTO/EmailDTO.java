package com.example.Expenses.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailDTO {
    private String subject;
    private String message;
}
