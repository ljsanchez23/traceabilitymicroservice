package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper.util;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document.ReportDocument;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper.IReportDocumentMapper;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class IReportDocumentMapperImpl implements IReportDocumentMapper {
    @Override
    public ReportDocument toDocument(Report report) {
        if (report == null) {
            return null;
        }

        ReportDocument reportDocument = new ReportDocument();
        reportDocument.setOrderId(report.getOrderId());
        reportDocument.setCustomerId(report.getCustomerId());
        reportDocument.setEmployeeId(report.getEmployeeId());

        reportDocument.setCreationTime(report.getCreationTime().atZone(ZoneId.systemDefault()).toInstant());

        reportDocument.setStatusHistory(
                report.getStatusHistory().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().atZone(ZoneId.systemDefault()).toInstant()
                        ))
        );

        return reportDocument;
    }

    @Override
    public Report toModel(ReportDocument reportDocument) {
        if (reportDocument == null) {
            return null;
        }

        Report report = new Report();
        report.setOrderId(reportDocument.getOrderId());
        report.setEmployeeId(reportDocument.getEmployeeId());
        report.setCustomerId(reportDocument.getCustomerId());

        report.setCreationTime(reportDocument.getCreationTime()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());

        report.setStatusHistory(
                reportDocument.getStatusHistory().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        ))
        );

        return report;
    }
}
