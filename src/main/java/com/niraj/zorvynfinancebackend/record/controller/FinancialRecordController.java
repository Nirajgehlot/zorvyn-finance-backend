package com.niraj.zorvynfinancebackend.record.controller;

import com.niraj.zorvynfinancebackend.record.dto.*;
import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import com.niraj.zorvynfinancebackend.record.service.FinancialRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/records")
public class FinancialRecordController
{
    private final FinancialRecordService service;

    public FinancialRecordController(FinancialRecordService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse createRecord(@Valid @RequestBody CreateRecordRequest request) {
        return service.createRecord(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public Page<RecordResponse> getAllRecords(
            @PageableDefault(size = 5, sort = "recordDate") Pageable pageable) {
        return service.getAllRecords(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public RecordResponse getRecordById(@PathVariable Long id) {
        return service.getRecordById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RecordResponse updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecordRequest request
    ) {
        return service.updateRecord(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRecord(@PathVariable Long id) {
        service.deleteRecord(id);
        return "Record deleted successfully";
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('ADMIN','ANALYST')")
    public List<RecordResponse> filterRecords(
            @RequestParam(required = false) RecordType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return service.filterRecords(type, category, startDate, endDate);
    }
}