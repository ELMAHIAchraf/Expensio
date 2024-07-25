package com.example.Expenses.Service.IncomeService;

import com.example.Expenses.DTO.IncomeDTO;
import com.example.Expenses.Entity.Income;

public interface IncomeService {
    IncomeDTO createIncome(Income income);
    IncomeDTO updateIncome(long id, Income income);
    double getIncome();
    boolean hasPermissionToAccessExpense(long expenseId);


}
