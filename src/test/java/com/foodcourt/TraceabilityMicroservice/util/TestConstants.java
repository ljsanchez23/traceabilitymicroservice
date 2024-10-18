package com.foodcourt.TraceabilityMicroservice.util;

public class TestConstants {
    public static final Long CUSTOMER_ID = 1L;
    public static final Long ORDER_ID = 1L;
    public static final Long EMPLOYEE_ID = 1L;
    public static final String PENDING_STATUS = "pending";
    public static final String READY_STATUS = "ready";
    public static final String ASSIGNED_STATUS = "assigned";
    public static final String SHOULD_START_REPORT_WHEN_VALID_DATA =
            "Should start report with valid data";
    public static final String SHOULD_GENERATE_REPORT_FOR_AUTH_USER =
            "Should generate report for authenticated user";
    public static final String SHOULD_THROW_EXCEPTION_WHEN_NO_REPORT_IS_FOUND =
            "Should throw exception when no report is found";
    public static final String SHOULD_ADD_STATUS_TO_EXISTING_REPORT =
            "Should add status to existing report";
    public static final String SHOULD_ASSIGN_EMPLOYEE_TO_AN_ORDER =
            "Should assign employee to an order";
    public static final String SHOULD_THROW_EXCEPTION_WHEN_REPORT_NOT_FOUND =
            "Should throw exception when report is not found";
}


}
