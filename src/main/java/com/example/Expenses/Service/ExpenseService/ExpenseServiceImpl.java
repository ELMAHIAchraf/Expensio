package com.example.Expenses.Service.ExpenseService;

import com.example.Expenses.Config.MyUser;
import com.example.Expenses.DTO.Category;
import com.example.Expenses.DTO.ExpenseCategoryDTO;
import com.example.Expenses.DTO.ExpenseDTO;
import com.example.Expenses.DTO.StatsDTO;
import com.example.Expenses.Entity.Expense;
import com.example.Expenses.Entity.User;
import com.example.Expenses.Exception.InsufficientBalanceException;
import com.example.Expenses.Exception.ResourceNotFoundException;
import com.example.Expenses.Repository.ExpenseRepository;
import com.example.Expenses.Repository.IncomeRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private ExpenseRepository expenseRepository;
    private IncomeRepository incomeRepository;

    private ModelMapper modelMapper;

    @Override
    public ExpenseDTO createExpense(Expense expenseDTO) {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        double income = incomeRepository.getCreationDate(month, year, user).orElse(0.0);

        List<LocalDateTime> thisMonthStartAndEnd= getStartAndEndOfTheMonth();
        double totalExpense = expenseRepository.findExpensesTotal(
                thisMonthStartAndEnd.get(0).toLocalDate(),
                thisMonthStartAndEnd.get(1).toLocalDate(),
                user).orElse(0.0);
        if(income < (expenseDTO.getAmount()+totalExpense)){
            throw  new InsufficientBalanceException("Insufficient balance");
        }
        expenseDTO.getUser().setId(MyUser.getId());
        return modelMapper.map(expenseRepository.save(modelMapper.map(expenseDTO, Expense.class)), ExpenseDTO.class);
    }

    @Override
    public ExpenseDTO updateExpense(long id, ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Expense", "id", id));

        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        return modelMapper.map(expenseRepository.save(expense), ExpenseDTO.class);
    }

    @Override
    public String deleteExpense(long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Expense", "id", id));
        expenseRepository.delete(expense);
        return "The Expense has been deleted";
    }

    @Override
    public Page<ExpenseCategoryDTO> getExpenses(Integer pageNo, Integer pageSize, String sortBy) {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Expense> expensesPages = expenseRepository.findByUser(user, pageable);
        List<ExpenseCategoryDTO> expenses = getLimitedExpensesHelper(expensesPages.getContent());
        return new PageImpl<>(expenses, pageable, expensesPages.getTotalElements());
    }

    @Override
    public Map<String, Object> getRecentExpenses() {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        List<Expense> expenses = expenseRepository.findTop5ByUserOrderByCreationDateDesc(user);
        LocalDate now= LocalDate.now();
        int firstDay = now.getDayOfMonth();
        LocalDate startDate = now.minusDays(firstDay-1);
        LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());

        Optional<Double> expensesTotal = expenseRepository.findExpensesTotal(startDate, endDate, user);
        double expenseTotalResp= expensesTotal.orElse(0.0);
        return Map.of("expenses", getLimitedExpensesHelper(expenses) , "expensesTotal", expenseTotalResp);
    }

    @Override
    public List<Map<Long, Double>> getCategoriesStatistics() {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
       List<LocalDateTime> thisMonthStartAndEnd= getStartAndEndOfTheMonth();
        return expenseRepository.findCategoryStatistics(thisMonthStartAndEnd.get(0), thisMonthStartAndEnd.get(1), user);

    }

    @Override
    public List<Map<LocalDate, Double>> getWeeklyStatistics() {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        LocalDate date = LocalDate.now();
        LocalDate startDate = date.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate endDate = date.minusWeeks(1).with(DayOfWeek.SUNDAY);
        return expenseRepository.findWeeklyStatistics(startDate, endDate, user);
    }

    @Override
    public List<Map<Long, Double>> getMonthlyStatistics() {
        //Removi minusMonths bach testi
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        LocalDate date = LocalDate.now().minusMonths(1);
        int month = date.getMonthValue();
        int year = date.getYear();

        return  expenseRepository.findMonthlyStatistics(month, year, user);
    }

    @Override
    public List<Map<Object, Object>> getExpensesStats(StatsDTO data) {
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = modelMapper.map(MyUser, User.class);
        return expenseRepository.getExpensesStats(data.getStartDate(), data.getEndDate(), data.getDimension(), user);
    }
    public boolean hasPermissionToAccessExpense(long expenseId){
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Expense expense = expenseRepository.findById(expenseId).orElseThrow();
        return user.getId()==expense.getUser().getId();
    }
    public List<ExpenseCategoryDTO> getLimitedExpensesHelper(List<Expense> expenses){
        Gson gson = new Gson();
        InputStream inputStream = Category.class.getResourceAsStream("/Data/Categories.json");
        InputStreamReader reader = new InputStreamReader(inputStream);

        List<Category> categories = List.of(gson.fromJson(reader, Category[].class));
        return expenses.stream().map(expense ->{
            ExpenseCategoryDTO expenseCategory = modelMapper.map(expense, ExpenseCategoryDTO.class);
            Category rightCategory = categories.stream()
                    .filter(category->category.getId()==expense.getCategory())
                    .findFirst()
                    .orElse(null);
            expenseCategory.setCategory(rightCategory);
            return expenseCategory;
        }).collect(Collectors.toList());

    }
    public List<LocalDateTime> getStartAndEndOfTheMonth(){
        LocalDate date= LocalDate.now();
        YearMonth year = YearMonth.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth());
        LocalDateTime startDate = LocalDateTime.of(date.getYear(), date.getMonth(),1, 0,0,0);
        LocalDateTime endDate = LocalDateTime.of(date.getYear(), date.getMonth(), year.lengthOfMonth(), 23,59,59);
        return List.of(startDate, endDate);
    }
    public User getAuthenticatedUser(){
        MyUser MyUser = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return modelMapper.map(MyUser, User.class);
    }



}
