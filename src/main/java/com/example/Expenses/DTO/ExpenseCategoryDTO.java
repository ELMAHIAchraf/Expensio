package com.example.Expenses.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExpenseCategoryDTO {
    private long id;
    private String description;
    private double amount;
    private Category category;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
