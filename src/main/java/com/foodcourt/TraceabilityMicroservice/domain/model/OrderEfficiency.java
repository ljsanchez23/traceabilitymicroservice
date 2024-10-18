package com.foodcourt.TraceabilityMicroservice.domain.model;

import java.time.Duration;

public class OrderEfficiency {
    private Long orderId;
    private Long employeeId;
    private Duration timeTaken;

    public OrderEfficiency(Long orderId, Long employeeId, Duration timeTaken) {
        this.orderId = orderId;
        this.employeeId = employeeId;
        this.timeTaken = timeTaken;
    }

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

    public Duration getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Duration timeTaken) {
        this.timeTaken = timeTaken;
    }

    @Override
    public String toString() {
        return "OrderEfficiency{" +
                "orderId=" + orderId +
                ", employeeId=" + employeeId +
                ", timeTaken=" + timeTaken +
                '}';
    }
}
