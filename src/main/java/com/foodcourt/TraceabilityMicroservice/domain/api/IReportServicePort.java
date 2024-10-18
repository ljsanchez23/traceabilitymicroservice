package com.foodcourt.TraceabilityMicroservice.domain.api;

public interface IReportServicePort {
    void startReport(Long customerId, Long orderId, String status);
    String generateReport();
    void addStatusToReport(Long orderId, String status);
    void assignEmployee(Long employeeId, Long orderId, String status);
}
