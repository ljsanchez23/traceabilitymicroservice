package com.foodcourt.TraceabilityMicroservice.domain.api.usecase;

import com.foodcourt.TraceabilityMicroservice.domain.api.IReportServicePort;
import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IAuthenticationPort;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import com.foodcourt.TraceabilityMicroservice.domain.util.Constants;
import com.foodcourt.TraceabilityMicroservice.domain.util.ReportFormatter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportUseCase implements IReportServicePort {

    private final IReportPersistencePort reportPersistencePort;
    private final IAuthenticationPort authenticationPort;

    public ReportUseCase(IReportPersistencePort reportPersistencePort, IAuthenticationPort authenticationPort) {
        this.reportPersistencePort = reportPersistencePort;
        this.authenticationPort = authenticationPort;
    }

    @Override
    public void startReport(Long customerId, Long orderId, String status) {
        Long employeeId = null;
        LocalDateTime creationTime = LocalDateTime.now();
        Map<String, LocalDateTime> statusHistory = new HashMap<>();
        statusHistory.put(status, creationTime);
        Report report = new Report(orderId, employeeId, customerId, creationTime, statusHistory);
        reportPersistencePort.saveReport(report);
    }

    @Override
    public String generateReport() {
        Long customerId = authenticationPort.getAuthenticatedUserId();
        List<Report> reports = reportPersistencePort.getReportsByCustomerId(customerId);

        if (reports.isEmpty()) {
            throw new ReportNotFoundException(Constants.REPORT_NOT_FOUND);
        }

        StringBuilder reportBuilder = new StringBuilder();
        reports.forEach(report -> reportBuilder.append(ReportFormatter.formatReport(report))
                .append(Constants.APPEND_UTIL));

        return reportBuilder.toString().trim();
    }

    @Override
    public void addStatusToReport(Long orderId, String status) {
        Report report = reportPersistencePort.findReportByOrderId(orderId);

        if (report == null) {
            throw new ReportNotFoundException(Constants.REPORT_NOT_FOUND);
        }

        Map<String, LocalDateTime> statuses = report.getStatusHistory();
        statuses.put(status, LocalDateTime.now());

        reportPersistencePort.saveReport(report);
    }

    @Override
    public void assignEmployee(Long employeeId, Long orderId, String status){
        Report report = reportPersistencePort.findReportByOrderId(orderId);
        Map<String, LocalDateTime> statuses = report.getStatusHistory();
        report.setEmployeeId(employeeId);
        statuses.put(status, LocalDateTime.now());
        reportPersistencePort.saveReport(report);
    }

    @Override
    public Map<Long, Double> calculateEmployeeRanking() {
        List<OrderEfficiency> efficiencies = reportPersistencePort.calculateOrderEfficiencies();
        return efficiencies.stream()
                .collect(Collectors.groupingBy(
                        OrderEfficiency::getEmployeeId,
                        Collectors.averagingDouble(e -> e.getTimeTaken().toMinutes())
                ));
    }

    @Override
    public List<OrderEfficiency> getAllOrderEfficiencies() {
        List<Report> reports = reportPersistencePort.getAllReports();

        return reports.stream()
                .filter(report -> report.getStatusHistory().containsKey(Constants.PENDING_STATUS)
                        && report.getStatusHistory().containsKey(Constants.DELIVERED_STATUS))
                .map(report -> {
                    LocalDateTime startTime = report.getStatusHistory().get(Constants.PENDING_STATUS);
                    LocalDateTime endTime = report.getStatusHistory().get(Constants.DELIVERED_STATUS);
                    Duration duration = Duration.between(startTime, endTime);
                    return new OrderEfficiency(report.getOrderId(), report.getEmployeeId(), duration);
                })
                .toList();
    }
}
