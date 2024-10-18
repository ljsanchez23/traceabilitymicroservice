package com.foodcourt.TraceabilityMicroservice.adapters.driving.mapper.impl;

import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.OrderEfficiencyResponse;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.ReportResponse;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.mapper.IReportResponseMapper;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class ReportResponseMapperImpl implements IReportResponseMapper {

    @Override
    public List<ReportResponse> toReportResponseList(Map<Long, Double> employeeRanking) {
        return employeeRanking.entrySet().stream()
                .map(entry -> new ReportResponse(
                        entry.getKey(),
                        formatTime(entry.getValue())
                ))
                .toList();
    }

    @Override
    public List<OrderEfficiencyResponse> toOrderEfficiencyResponseList(List<OrderEfficiency> efficiencies) {
        return efficiencies.stream()
                .map(efficiency -> new OrderEfficiencyResponse(
                        efficiency.getOrderId(),
                        efficiency.getEmployeeId(),
                        formatDuration(efficiency.getTimeTaken())
                ))
                .toList();
    }

    // Formateo de tiempo en minutos u horas para ranking (Double)
    private String formatTime(Double totalMinutes) {
        long minutes = Math.round(totalMinutes);
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;

        if (hours > 0) {
            return String.format("%d hr %02d min", hours, remainingMinutes);
        } else {
            return String.format("%d min", remainingMinutes);
        }
    }

    // Formateo de Duration para OrderEfficiency
    private String formatDuration(Duration duration) {
        long totalMinutes = duration.toMinutes();
        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        if (hours > 0) {
            return String.format("%d hr %02d min", hours, minutes);
        } else {
            return String.format("%d min", minutes);
        }
    }
}
