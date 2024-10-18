package com.foodcourt.TraceabilityMicroservice.adapters.util;

public class AdaptersConstants {
    public static final String REPORT_CONTROLLER_URL = "/report";
    public static final String REPORT_COLLECTION_NAME = "report";
    public static final String REPORT_NOT_FOUND = "Report not found";
    public static final String CREATE_REPORT_ENDPOINT_URL = "/{customerId}/{orderId}/create";
    public static final String ADD_STATUS_TO_REPORT_ENDPOINT_URL = "/{orderId}/addStatus";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_URL = "/{employeeId}/{orderId}/assignEmployee";
    public static final String USER_ID_FROM_TOKEN = "userId";
    public static final String GENERATE_REPORT_ENDPOINT_URL = "/generate";
    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String UNAUTHORIZED = "401";
    public static final String CREATE_REPORT_ENDPOINT_SUMMARY = "Create a new report";
    public static final String CREATE_REPORT_ENDPOINT_DESCRIPTION =
            "This endpoint creates a new report for a given customer and order.";
    public static final String CREATE_REPORT_ENDPOINT_OK_DESCRIPTION = "The report was created successfully.";
    public static final String CREATE_REPORT_ENDPOINT_BAD_REQUEST_DESCRIPTION =
            "Invalid request. Please check the input parameters.";
    public static final String CREATE_REPORT_ENDPOINT_UNAUTHORIZED_DESCRIPTION =
            "Unauthorized request. Please provide a valid JWT token.";
    public static final String ADD_STATUS_TO_REPORT_ENDPOINT_SUMMARY = "Add status to an existing report";
    public static final String ADD_STATUS_TO_REPORT_ENDPOINT_DESCRIPTION =
            "This endpoint adds a new status to the specified report.";
    public static final String ADD_STATUS_TO_REPORT_OK_DESCRIPTION = "Status added successfully.";
    public static final String ADD_STATUS_TO_REPORT_BAD_REQUEST_DESCRIPTION =
            "Invalid status or orderId. Please check the request.";
    public static final String ADD_STATUS_TO_REPORT_UNAUTHORIZED_DESCRIPTION =
            "Unauthorized request. Please provide a valid JWT token.";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_SUMMARY = "Assign an employee to an order";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_DESCRIPTION =
            "This endpoint assigns an employee to a specific order.";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_OK_DESCRIPTION = "Employee assigned successfully.";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_BAD_REQUEST_DESCRIPTION =
            "Invalid employeeId or orderId. Please check the input parameters.";
    public static final String ASSIGN_EMPLOYEE_ENDPOINT_UNAUTHORIZED_DESCRIPTION =
            "Unauthorized request. Please provide a valid JWT token.";
    public static final String GENERATE_REPORT_ENDPOINT_SUMMARY = "Generate a report";
    public static final String GENERATE_REPORT_ENDPOINT_DESCRIPTION =
            "This endpoint generates a report summarizing the status and progress of orders.";
    public static final String GENERATE_REPORT_ENDPOINT_OK_DESCRIPTION = "Report generated successfully.";
    public static final String GENERATE_REPORT_ENDPOINT_BAD_REQUEST_DESCRIPTION =
            "Failed to generate report. Please check the request parameters.";
    public static final String GENERATE_REPORT_ENDPOINT_UNAUTHORIZED_DESCRIPTION =
            "Unauthorized request. Please provide a valid JWT token.";
    public static final String GET_ALL_ORDERS_EFFICIENCY_ENDPOINT_URL = "/generate/efficiency";
    public static final String CALCULATE_EMPLOYEE_RANKING_ENDPOINT_URL = "/generate/ranking";
    public static final String GET_ALL_ORDERS_ENDPOINT_SUMMARY = "Retrieve all order efficiencies";
    public static final String GET_ALL_ORDERS_ENDPOINT_DESCRIPTION = "Fetches a list of all orders along with their efficiency metrics.";
    public static final String GET_ALL_ORDERS_ENDPOINT_OK_DESCRIPTION = "Successfully retrieved the list of order efficiencies.";
    public static final String GET_ALL_ORDERS_ENDPOINT_BAD_REQUEST_DESCRIPTION = "Invalid request. Please check the parameters and try again.";
    public static final String GET_ALL_ORDERS_ENDPOINT_UNAUTHORIZED_DESCRIPTION = "Unauthorized access. Authentication is required.";
    public static final String CALCULATE_EMPLOYEE_ENDPOINT_SUMMARY = "Calculate employee performance ranking";
    public static final String CALCULATE_EMPLOYEE_ENDPOINT_DESCRIPTION = "Generates a ranking of employees based on the average time taken to complete orders.";
    public static final String CALCULATE_EMPLOYEE_ENDPOINT_OK_DESCRIPTION = "Successfully calculated the employee performance ranking.";
    public static final String CALCULATE_EMPLOYEE_ENDPOINT_BAD_REQUEST_DESCRIPTION = "Invalid request. Please check the parameters and try again.";
    public static final String CALCULATE_EMPLOYEE_ENDPOINT_UNAUTHORIZED_DESCRIPTION = "Unauthorized access. Authentication is required.";
}
