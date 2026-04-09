package com.niraj.zorvynfinancebackend.record.service;

import com.niraj.zorvynfinancebackend.common.exception.ResourceNotFoundException;
import com.niraj.zorvynfinancebackend.record.dto.CreateRecordRequest;
import com.niraj.zorvynfinancebackend.record.dto.RecordResponse;
import com.niraj.zorvynfinancebackend.record.dto.UpdateRecordRequest;
import com.niraj.zorvynfinancebackend.record.entity.FinancialRecord;
import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import com.niraj.zorvynfinancebackend.record.repository.FinancialRecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialRecordService {

    private final FinancialRecordRepository repository;

    public FinancialRecordService(FinancialRecordRepository repository) {
        this.repository = repository;
    }

    public RecordResponse createRecord(CreateRecordRequest request) {

        FinancialRecord record = new FinancialRecord();
        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setRecordDate(request.getRecordDate());
        record.setDescription(request.getDescription());
        record.setCreatedByUserId(request.getCreatedByUserId());
        record.setDeleted(false);

        FinancialRecord saved = repository.save(record);

        return new RecordResponse(
                saved.getId(),
                saved.getAmount(),
                saved.getType(),
                saved.getCategory(),
                saved.getRecordDate(),
                saved.getDescription()
        );
    }

    public Page<RecordResponse> getAllRecords(Pageable pageable) {
        return repository.findAllByDeletedFalse(pageable)
                .map(record -> new RecordResponse(
                        record.getId(),
                        record.getAmount(),
                        record.getType(),
                        record.getCategory(),
                        record.getRecordDate(),
                        record.getDescription()
                ));
    }

    public RecordResponse getRecordById(Long id) {
        FinancialRecord record = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        return new RecordResponse(
                record.getId(),
                record.getAmount(),
                record.getType(),
                record.getCategory(),
                record.getRecordDate(),
                record.getDescription()
        );
    }

    public RecordResponse updateRecord(Long id, UpdateRecordRequest request) {

        FinancialRecord record = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setAmount(request.getAmount());
        record.setType(request.getType());
        record.setCategory(request.getCategory());
        record.setRecordDate(request.getRecordDate());
        record.setDescription(request.getDescription());

        FinancialRecord updated = repository.save(record);

        return new RecordResponse(
                updated.getId(),
                updated.getAmount(),
                updated.getType(),
                updated.getCategory(),
                updated.getRecordDate(),
                updated.getDescription()
        );
    }

    public void deleteRecord(Long id) {

        FinancialRecord record = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        record.setDeleted(true);
        repository.save(record);
    }

    public List<RecordResponse> filterRecords(
            RecordType type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<FinancialRecord> records;

        if (type != null && category != null && startDate != null && endDate != null) {
            records = repository.findByTypeAndCategoryAndRecordDateBetweenAndDeletedFalse(type, category, startDate, endDate);
        } else if (type != null) {
            records = repository.findByTypeAndDeletedFalse(type);
        } else if (category != null) {
            records = repository.findByCategoryAndDeletedFalse(category);
        } else if (startDate != null && endDate != null) {
            records = repository.findByRecordDateBetweenAndDeletedFalse(startDate, endDate);
        } else {
            records = repository.findAllByDeletedFalse(Pageable.unpaged()).getContent();
        }

        return records.stream()
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