package com.example.Expenses.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class StatsDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private String dimension;
}
