package com.example.Expenses.DTO;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ExpenseAndTotalDTO {
    private List<ExpenseCategoryDTO> expenses;
    private double expensesTotal;
}
