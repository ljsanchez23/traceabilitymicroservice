package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.repository;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document.ReportDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IReportRepository extends MongoRepository<ReportDocument, String> {
    Optional<ReportDocument> findByOrderId(Long orderId);
    List<ReportDocument> findByCustomerId(Long customerId);
}
