package com.niraj.zorvynfinancebackend.record.dto;

import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class CreateRecordRequest
{
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotNull(message = "Type is required")
    private RecordType type;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Record date is required")
    private LocalDate recordDate;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "CreatedByUserId is required")
    private Long createdByUserId;
}