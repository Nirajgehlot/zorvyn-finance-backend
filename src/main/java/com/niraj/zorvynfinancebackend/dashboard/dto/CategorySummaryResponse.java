package com.niraj.zorvynfinancebackend.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategorySummaryResponse
{
    private String category;
    private Double total;
}