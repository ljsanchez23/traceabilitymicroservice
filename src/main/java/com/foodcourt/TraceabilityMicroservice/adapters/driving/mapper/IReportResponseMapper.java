package com.foodcourt.TraceabilityMicroservice.adapters.driving.mapper;

import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.OrderEfficiencyResponse;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.ReportResponse;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;

import java.util.List;
import java.util.Map;

public interface IReportResponseMapper {
    List<ReportResponse> toReportResponseList(Map<Long, Double> employeeRanking);
    List<OrderEfficiencyResponse> toOrderEfficiencyResponseList(List<OrderEfficiency> efficiencies);
}
