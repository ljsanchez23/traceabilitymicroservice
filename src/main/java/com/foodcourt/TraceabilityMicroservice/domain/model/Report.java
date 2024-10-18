package com.foodcourt.TraceabilityMicroservice.domain.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Report {
    private Long orderId;
    private Long employeeId;
    private Long customerId;
    private LocalDateTime creationTime;
    private Map<String, LocalDateTime> statusHistory;

    public Report(Long orderId, Long employeeId, Long customerId, LocalDateTime creationTime, Map<String, LocalDateTime> statusHistory) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.creationTime = creationTime;
        this.statusHistory = statusHistory;
    }

    public Report() {}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Map<String, LocalDateTime> getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(Map<String, LocalDateTime> statusHistory) {
        this.statusHistory = statusHistory;
    }

    public void addStatus(String status) {
        statusHistory.put(status, LocalDateTime.now());
    }

}
