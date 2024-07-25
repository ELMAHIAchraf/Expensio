package com.example.Expenses.Repository;

import com.example.Expenses.Entity.Expense;
import com.example.Expenses.Entity.User;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

//    List<Expense> findTop30ByUserOrderByCreationDate(User user);
    Page<Expense> findByUser(User user, Pageable pageable);
    List<Expense> findTop5ByUserOrderByCreationDateDesc(User user);
    @Query("SELECT e.category AS category, SUM(e.amount) AS sumAmount " +
            "FROM Expense AS e " +
            "WHERE e.creationDate BETWEEN :startDate AND :endDate AND e.user= :user " +
            "GROUP BY e.category")
    List<Map<Long,Double>> findCategoryStatistics(LocalDateTime startDate, LocalDateTime endDate, User user);
    @Query(" SELECT Date(e.creationDate) AS date, SUM(e.amount) AS amount " +
            "FROM Expense AS e " +
            "WHERE Date(e.creationDate) " +
            "BETWEEN :startDate AND :endDate AND e.user= :user " +
            "GROUP BY Date(e.creationDate)")
    List<Map<LocalDate, Double>> findWeeklyStatistics(LocalDate startDate, LocalDate endDate, User user);

    @Query("SELECT WEEK(e.creationDate) AS week, SUM(e.amount) AS amount " +
            "FROM Expense AS e " +
            "WHERE MONTH(e.creationDate) = :month AND YEAR(e.creationDate) = :year AND e.user= :user " +
            "GROUP BY WEEK(e.creationDate)")
    List<Map<Long, Double>> findMonthlyStatistics(int month, int year, User user);

    @Query("SELECT SUM(e.amount) AS total " +
            "FROM Expense AS e " +
            "WHERE Date(e.creationDate) BETWEEN :startDate AND :endDate AND e.user= :user")
    Optional<Double> findExpensesTotal(LocalDate startDate, LocalDate endDate, User user);

//    @Query("SELECT " +
//            "CASE" +
//            "   WHEN :dimension = 'category' THEN e.category" +
//            "   ELSE e.creationDate " +
//            "END AS dimension, "+
//            "SUM(e.amount) AS expensesStats " +
//            "FROM Expense AS e " +
//            "WHERE e.creationDate BETWEEN :startDate AND :endDate " +
//            "GROUP BY " +
//            "CASE " +
//            "   WHEN :dimension = 'category' THEN e.category " +
//            "   ELSE e.creationDate " +
//            "END")
    @Query(
        "SELECT  " +
            "CASE WHEN :dimension = 'date' THEN Date(e.creationDate) END as date, " +
            "CASE WHEN :dimension = 'category' THEN e.category END as category, " +
            "SUM(e.amount) AS expensesStats " +
        "FROM Expense AS e " +
        "WHERE Date(e.creationDate) " +
        "BETWEEN :startDate AND :endDate AND e.user= :user " +
        "GROUP BY " +
        "CASE " +
            "WHEN :dimension = 'category' THEN e.category " +
            "ELSE e.creationDate " +
        "END"
    )
    List<Map<Object, Object>> getExpensesStats(LocalDate startDate, LocalDate endDate, String dimension, User user);
}
