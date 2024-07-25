package com.example.Expenses.Service.ExpenseService;

import com.example.Expenses.DTO.ExpenseCategoryDTO;
import com.example.Expenses.DTO.ExpenseDTO;
import com.example.Expenses.DTO.StatsDTO;
import com.example.Expenses.Entity.Expense;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ExpenseService {
    ExpenseDTO createExpense(Expense expenseDTO);
    ExpenseDTO updateExpense(long id, ExpenseDTO expenseDTO);
    String deleteExpense(long id);
    Page<ExpenseCategoryDTO> getExpenses(Integer pageNo, Integer pageSize, String sortBy);
    Map<String, Object> getRecentExpenses() ;
    List<Map<Long, Double>> getCategoriesStatistics();

    List<Map<LocalDate, Double>> getWeeklyStatistics();

    List<Map<Long, Double>> getMonthlyStatistics();

    List<Map<Object, Object>> getExpensesStats(StatsDTO data);

    boolean hasPermissionToAccessExpense(long expenseId);



}
