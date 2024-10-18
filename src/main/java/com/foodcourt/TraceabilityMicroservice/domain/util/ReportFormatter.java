package com.foodcourt.TraceabilityMicroservice.domain.util;

import com.foodcourt.TraceabilityMicroservice.domain.model.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ReportFormatter {
    private ReportFormatter() {
    }

    public static String formatReport(Report report) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);

        StringBuilder builder = new StringBuilder();
        builder.append(Constants.ORDER_ID).append(report.getOrderId()).append(Constants.SKIP_LINE_APPEND);
        builder.append(Constants.EMPLOYEE_ASSIGNED)
                .append(report.getEmployeeId() != null ? report.getEmployeeId() : Constants.NO_ASSIGNED).append(Constants.SKIP_LINE_APPEND);
        builder.append(Constants.DATE_OF_CREATION).append(report.getCreationTime().format(formatter)).append(Constants.SKIP_LINE_APPEND);

        builder.append(Constants.ORDER_HISTORY);
        for (Map.Entry<String, LocalDateTime> entry : report.getStatusHistory().entrySet()) {
            builder.append(Constants.DASH_APPEND).append(entry.getKey())
                    .append(Constants.COLON_APPEND).append(entry.getValue().format(formatter)).append(Constants.SKIP_LINE_APPEND);
        }

        return builder.toString();
    }
}
