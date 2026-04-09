package com.niraj.zorvynfinancebackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DashboardSummaryResponse
{
    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;
}