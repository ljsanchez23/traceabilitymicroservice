package com.foodcourt.TraceabilityMicroservice.adapters.driving.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderEfficiencyResponse {
    private Long orderId;
    private Long employeeId;
    private String formattedTimeTaken;
}
