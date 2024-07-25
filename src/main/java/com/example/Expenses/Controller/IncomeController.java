package com.example.Expenses.Controller;


import com.example.Expenses.DTO.IncomeDTO;
import com.example.Expenses.Entity.Income;
import com.example.Expenses.Exception.UnauthorizedAccessException;
import com.example.Expenses.Service.IncomeService.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/income")
public class IncomeController {

    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<IncomeDTO> createIncome(@RequestBody Income income){
        IncomeDTO createdIncome  = incomeService.createIncome(income);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<IncomeDTO> updateIncome(@PathVariable long id, @RequestBody Income income){
        if(incomeService.hasPermissionToAccessExpense(id)) {
            IncomeDTO updatedIncome = incomeService.updateIncome(id, income);
            return new ResponseEntity<>(updatedIncome, HttpStatus.OK);
        }else{
            throw new UnauthorizedAccessException("User not authorized to access income");
        }
    }

    @GetMapping()
    public ResponseEntity<Double> getIncome() {
        double fetchedIncome = incomeService.getIncome();
        return  new ResponseEntity<>(fetchedIncome, HttpStatus.OK);
    }
}
