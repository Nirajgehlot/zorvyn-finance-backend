package com.niraj.zorvynfinancebackend.record.dto;

import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class RecordResponse {

    private Long id;
    private Double amount;
    private RecordType type;
    private String category;
    private LocalDate recordDate;
    private String description;
}