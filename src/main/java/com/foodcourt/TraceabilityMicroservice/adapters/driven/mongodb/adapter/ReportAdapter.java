package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.adapter;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper.IReportDocumentMapper;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.repository.IReportRepository;
import com.foodcourt.TraceabilityMicroservice.adapters.util.AdaptersConstants;
import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document.ReportDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    protected Map<String, LocalDateTime> convertInstantMapToLocalDateTime(Map<String, Instant> instantMap) {
        return instantMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().atZone(ZoneId.systemDefault()).toLocalDateTime()
                ));
    }
}
