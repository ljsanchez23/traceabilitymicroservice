package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.adapter;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper.IReportDocumentMapper;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.repository.IReportRepository;
import com.foodcourt.TraceabilityMicroservice.adapters.util.AdaptersConstants;
import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document.ReportDocument;
import com.foodcourt.TraceabilityMicroservice.domain.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class ReportAdapter implements IReportPersistencePort {

    private final IReportRepository reportRepository;
    private final IReportDocumentMapper reportDocumentMapper;

    @Override
    public void saveReport(Report report) {
        Optional<ReportDocument> existingDocument = reportRepository.findByOrderId(report.getOrderId());

        ReportDocument document = reportDocumentMapper.toDocument(report);
        existingDocument.ifPresent(existing -> document.setId(existing.getId()));

        reportRepository.save(document);
    }

    @Override
    public Map<String, LocalDateTime> getOrderStatusHistory(Long orderId) {
        return reportRepository.findByOrderId(orderId)
                .map(report -> convertInstantMapToLocalDateTime(report.getStatusHistory()))
                .orElseThrow(() -> new ReportNotFoundException(AdaptersConstants.REPORT_NOT_FOUND));
    }


    @Override
    public Report findReportByOrderId(Long orderId) {
        return reportRepository.findByOrderId(orderId)
                .map(reportDocumentMapper::toModel)
                .orElseThrow(() -> new ReportNotFoundException(AdaptersConstants.REPORT_NOT_FOUND));
    }

    @Override
    public List<Report> getReportsByCustomerId(Long customerId) {
        return reportRepository.findByCustomerId(customerId).stream()
                .map(reportDocumentMapper::toModel)
                .toList();
    }

    @Override
    public List<OrderEfficiency> calculateOrderEfficiencies() {
        List<ReportDocument> reports = reportRepository.findAll();

        return reports.stream()
                .map(this::calculateOrderEfficiency)
                .toList();
    }

    @Override
    public List<Report> getAllReports() {
        List<ReportDocument> reportDocuments = reportRepository.findAll();
        return reportDocuments.stream()
                .map(reportDocumentMapper::toModel)
                .toList();
    }

    protected OrderEfficiency calculateOrderEfficiency(ReportDocument report) {
        Instant start = report.getStatusHistory().get(Constants.PENDING_STATUS);
        Instant end = report.getStatusHistory().get(Constants.DELIVERED_STATUS);

        Duration duration = Duration.between(start, end);

        return new OrderEfficiency(
                report.getOrderId(),
                report.getEmployeeId(),
                duration
        );
    }
    protected Map<String, LocalDateTime> convertInstantMapToLocalDateTime(Map<String, Instant> instantMap) {
        return instantMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().atZone(ZoneId.systemDefault()).toLocalDateTime()
                ));
    }
}
