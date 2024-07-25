package com.example.Expenses.DTO;

import com.example.Expenses.Entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.Default;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {
    private long id;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Amount is required")
    private double amount;
    @NotBlank(message = "Category is required")
    private int category;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
