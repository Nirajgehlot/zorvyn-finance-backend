package com.niraj.zorvynfinancebackend.dashboard.controller;

import com.niraj.zorvynfinancebackend.dashboard.dto.DashboardSummaryResponse;
import com.niraj.zorvynfinancebackend.dashboard.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.niraj.zorvynfinancebackend.dashboard.dto.CategorySummaryResponse;
import java.util.List;
import com.niraj.zorvynfinancebackend.dashboard.dto.MonthlyTrendResponse;
import com.niraj.zorvynfinancebackend.record.dto.RecordResponse;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController
{

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public DashboardSummaryResponse getSummary() {
        return service.getSummary();
    }

    @GetMapping("/category-summary")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<CategorySummaryResponse> getCategorySummary() {
        return service.getCategorySummary();
    }

    @GetMapping("/monthly-trend")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<MonthlyTrendResponse> getMonthlyTrend() {
        return service.getMonthlyTrend();
    }

    @GetMapping("/recent-activity")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST','VIEWER')")
    public List<RecordResponse> getRecentActivity() {
        return service.getRecentActivity();
    }
}