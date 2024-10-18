package com.foodcourt.TraceabilityMicroservice.util;

import com.foodcourt.TraceabilityMicroservice.domain.model.Report;

import java.time.LocalDateTime;
import java.util.Map;

public class TestDataFactory {

    public static Report createReport(Long orderId, Long employeeId, Map<String, LocalDateTime> statusHistory) {
        return new Report(
                orderId,
                employeeId,
                1L, // customerId
                LocalDateTime.now(), // creationTime
                statusHistory
        );
    }
}
