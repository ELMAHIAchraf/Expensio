package com.example.Expenses.Service.IncomeService;

import com.example.Expenses.Config.MyUser;
import com.example.Expenses.DTO.IncomeDTO;
import com.example.Expenses.Entity.Expense;
import com.example.Expenses.Entity.Income;
import com.example.Expenses.Entity.User;
import com.example.Expenses.Exception.ResourceNotFoundException;
import com.example.Expenses.Repository.IncomeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private IncomeRepository incomeRepository;
    private ModelMapper modelMapper;


    @Override
    public IncomeDTO createIncome(Income income) {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        income.getUser().setId(MyUser.getId());
        return modelMapper.map(incomeRepository.save(modelMapper.map(income, Income.class)), IncomeDTO.class);
    }

    @Override
    public IncomeDTO updateIncome(long id, Income income) {
        Income wantedIncome = incomeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Income", "id", id)
        );
         wantedIncome.setAmount(income.getAmount());
        return modelMapper.map(incomeRepository.save(wantedIncome), IncomeDTO.class);

    }

    @Override
    public double getIncome() {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        int month = LocalDate.now().minusMonths(1).getMonthValue();
        int year = LocalDate.now().getYear();
        return incomeRepository.getCreationDate(month, year, user).orElse(0.0);
    }

    public boolean hasPermissionToAccessExpense(long incomeId){
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Income income = incomeRepository.findById(incomeId).orElseThrow();
        return user.getId()==income.getUser().getId();
    }
}
