package com.foodcourt.TraceabilityMicroservice.adapters.driving.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReportResponse {
    private Long employeeId;
    private String averageTime;
}
