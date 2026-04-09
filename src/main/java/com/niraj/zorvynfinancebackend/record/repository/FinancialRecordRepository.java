package com.niraj.zorvynfinancebackend.record.repository;

import com.niraj.zorvynfinancebackend.record.entity.FinancialRecord;
import com.niraj.zorvynfinancebackend.record.enums.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {

    Optional<FinancialRecord> findByIdAndDeletedFalse(Long id);

    Page<FinancialRecord> findAllByDeletedFalse(Pageable pageable);

    List<FinancialRecord> findByTypeAndDeletedFalse(RecordType type);

    List<FinancialRecord> findByCategoryAndDeletedFalse(String category);

    List<FinancialRecord> findByRecordDateBetweenAndDeletedFalse(LocalDate startDate, LocalDate endDate);

    List<FinancialRecord> findByTypeAndCategoryAndRecordDateBetweenAndDeletedFalse(
            RecordType type,
            String category,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.type = :type AND r.deleted = false")
    Double getTotalAmountByType(@Param("type") RecordType type);

    @Query("SELECT r.category, COALESCE(SUM(r.amount), 0) FROM FinancialRecord r WHERE r.deleted = false GROUP BY r.category")
    List<Object[]> getCategorySummary();

    @Query("SELECT FUNCTION('DATE_FORMAT', r.recordDate, '%Y-%m'), COALESCE(SUM(r.amount), 0) " +
            "FROM FinancialRecord r WHERE r.deleted = false " +
            "GROUP BY FUNCTION('DATE_FORMAT', r.recordDate, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', r.recordDate, '%Y-%m')")
    List<Object[]> getMonthlyTrend();

    List<FinancialRecord> findAllByDeletedFalseOrderByCreatedAtDesc(Pageable pageable);
}