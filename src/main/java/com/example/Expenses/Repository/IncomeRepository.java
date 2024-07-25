package com.example.Expenses.Repository;

import com.example.Expenses.DTO.IncomeDTO;
import com.example.Expenses.Entity.Income;
import com.example.Expenses.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT e.amount FROM Income AS e " +
            "WHERE Month(e.creationDate) = :month AND Year(e.creationDate) = :year AND e.user= :user")
    Optional<Double> getCreationDate(int month, int year, User user);
}
