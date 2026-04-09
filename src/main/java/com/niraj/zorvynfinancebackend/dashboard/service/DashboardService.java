package com.niraj.zorvynfinancebackend.dashboard.service;

import com.niraj.zorvynfinancebackend.dashboard.dto.CategorySummaryResponse;
import com.niraj.zorvynfinancebackend.dashboard.dto.DashboardSummaryResponse;
import com.niraj.zorvynfinancebackend.dashboard.dto.MonthlyTrendResponse;
import com.niraj.zorvynfinancebackend.record.dto.RecordResponse;
import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import com.niraj.zorvynfinancebackend.record.repository.FinancialRecordRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService
{
    private final FinancialRecordRepository recordRepository;

    public DashboardService(FinancialRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public DashboardSummaryResponse getSummary() {
        Double totalIncome = recordRepository.getTotalAmountByType(RecordType.INCOME);
        Double totalExpense = recordRepository.getTotalAmountByType(RecordType.EXPENSE);
        Double netBalance = totalIncome - totalExpense;

        return new DashboardSummaryResponse(totalIncome, totalExpense, netBalance);
    }

    public List<CategorySummaryResponse> getCategorySummary() {
        return recordRepository.getCategorySummary()
                .stream()
                .map(obj -> new CategorySummaryResponse(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }

    public List<MonthlyTrendResponse> getMonthlyTrend() {
        return recordRepository.getMonthlyTrend()
                .stream()
                .map(obj -> new MonthlyTrendResponse(
                        (String) obj[0],
                        (Double) obj[1]
                ))
                .toList();
    }

    public List<RecordResponse> getRecentActivity() {
        return recordRepository.findAllByDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 5))
                .stream()
                .map(record -> new RecordResponse(
                        record.getId(),
                        record.getAmount(),
                        record.getType(),
                        record.getCategory(),
                        record.getRecordDate(),
                        record.getDescription()
                ))
                .toList();
    }
}