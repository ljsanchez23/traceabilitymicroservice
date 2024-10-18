package com.foodcourt.TraceabilityMicroservice.domain.spi;

import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IReportPersistencePort {
    void saveReport(Report report);
    Map<String, LocalDateTime> getOrderStatusHistory(Long orderId);
    Report findReportByOrderId(Long orderId);
    List<Report> getReportsByCustomerId(Long customerId);
    List<OrderEfficiency> calculateOrderEfficiencies();
    List<Report> getAllReports();
}
