package com.niraj.zorvynfinancebackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MonthlyTrendResponse
{
    private String month;
    private Double total;
}