package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document.ReportDocument;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;

public interface IReportDocumentMapper {
    ReportDocument toDocument(Report report);
    Report toModel(ReportDocument reportDocument);
}
