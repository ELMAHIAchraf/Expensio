package com.example.Expenses.Controller;


import com.example.Expenses.DTO.ExpenseCategoryDTO;
import com.example.Expenses.DTO.ExpenseDTO;
import com.example.Expenses.DTO.StatsDTO;
import com.example.Expenses.Entity.Expense;
import com.example.Expenses.Exception.UnauthorizedAccessException;
import com.example.Expenses.Service.ExpenseService.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("api/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody Expense expense){
        ExpenseDTO createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDTO> updateExpense(
            @PathVariable long id,
            @RequestBody ExpenseDTO expense
    ){
        if(expenseService.hasPermissionToAccessExpense(id)) {
            ExpenseDTO updatedExpense = expenseService.updateExpense(id, expense);
            return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
        }else{
            throw new UnauthorizedAccessException("User not authorized to access expenses");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable long id){
        if(expenseService.hasPermissionToAccessExpense(id)) {
            String deletedExpense = expenseService.deleteExpense(id);
            return new ResponseEntity<>(deletedExpense, HttpStatus.OK);
        }else{
            throw new UnauthorizedAccessException("User not authorized to access expenses");
        }
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryDTO>> getExpenses(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "creationDate") String sortBy
    ){
        Page<ExpenseCategoryDTO> expenses = expenseService.getExpenses(pageNo, pageSize, sortBy);
        return  new ResponseEntity<>(expenses.getContent(), HttpStatus.OK);
    }

    @GetMapping("/recent")
    public ResponseEntity<Map<String, Object> > getRecentExpenses(){
        Map<String, Object>  expenses = expenseService.getRecentExpenses();
        return  new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @GetMapping("/categoriesStatistics")
    public ResponseEntity<List<Map<Long,Double>>> getCategoriesStatistics(){
        List<Map<Long,Double>> statistics = expenseService.getCategoriesStatistics();
        return  new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("/weeklyReport")
    public ResponseEntity<List<Map<LocalDate, Double>>> getWeeklyStatistics(){
        List<Map<LocalDate, Double>> statistics = expenseService.getWeeklyStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
    @GetMapping("/monthlyReport")
    public ResponseEntity<List<Map<Long, Double>>> getMonthlyStatistics(){
        List<Map<Long, Double>> statistics = expenseService.getMonthlyStatistics();
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping("statistics")
    public ResponseEntity<List<Map<Object, Object>>> getExpensesStats(@RequestBody StatsDTO data){
        List<Map<Object, Object>> statistics = expenseService.getExpensesStats(data);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

}
