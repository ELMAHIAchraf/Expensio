package com.example.Expenses.DTO;

import com.example.Expenses.Entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO {
    private long id;
    @NotBlank(message = "Amount is required")
    private double amount;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
}
