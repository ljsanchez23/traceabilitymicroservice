package com.foodcourt.TraceabilityMicroservice.domain.api;

import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;

import java.util.List;
import java.util.Map;

public interface IReportServicePort {
    void startReport(Long customerId, Long orderId, String status);
    String generateReport();
    void addStatusToReport(Long orderId, String status);
    void assignEmployee(Long employeeId, Long orderId, String status);
    Map<Long, Double> calculateEmployeeRanking();
    List<OrderEfficiency> getAllOrderEfficiencies();
}
