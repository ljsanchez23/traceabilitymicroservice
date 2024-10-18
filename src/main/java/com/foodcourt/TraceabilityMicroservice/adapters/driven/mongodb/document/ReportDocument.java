package com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.document;

import com.foodcourt.TraceabilityMicroservice.adapters.util.AdaptersConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = AdaptersConstants.REPORT_COLLECTION_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDocument {
    @Id
    private String id;
    private Long orderId;
    private Long employeeId;
    private Long customerId;
    private Instant creationTime;
    private Map<String, Instant> statusHistory;
}
