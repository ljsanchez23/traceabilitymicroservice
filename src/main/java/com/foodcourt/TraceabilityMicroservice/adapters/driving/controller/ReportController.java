package com.foodcourt.TraceabilityMicroservice.adapters.driving.controller;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.OrderEfficiencyResponse;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.dto.ReportResponse;
import com.foodcourt.TraceabilityMicroservice.adapters.driving.mapper.IReportResponseMapper;
import com.foodcourt.TraceabilityMicroservice.adapters.util.AdaptersConstants;

import com.foodcourt.TraceabilityMicroservice.domain.api.IReportServicePort;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(AdaptersConstants.REPORT_CONTROLLER_URL)
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final IReportServicePort reportServicePort;
    private final IReportResponseMapper reportResponseMapper;

    @Operation(summary = AdaptersConstants.CREATE_REPORT_ENDPOINT_SUMMARY, description = AdaptersConstants.CREATE_REPORT_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.CREATE_REPORT_ENDPOINT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.CREATE_REPORT_ENDPOINT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.CREATE_REPORT_ENDPOINT_UNAUTHORIZED_DESCRIPTION)
    })
    @PostMapping(AdaptersConstants.CREATE_REPORT_ENDPOINT_URL)
    public ResponseEntity<Void> createReport(@PathVariable Long customerId, @PathVariable Long orderId, @RequestParam String status){
        reportServicePort.startReport(customerId, orderId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = AdaptersConstants.ADD_STATUS_TO_REPORT_ENDPOINT_SUMMARY, description = AdaptersConstants.ADD_STATUS_TO_REPORT_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.ADD_STATUS_TO_REPORT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.ADD_STATUS_TO_REPORT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.ADD_STATUS_TO_REPORT_UNAUTHORIZED_DESCRIPTION)
    })
    @PutMapping(AdaptersConstants.ADD_STATUS_TO_REPORT_ENDPOINT_URL)
    public void addStatusToReport(@PathVariable Long orderId,
                                  @RequestParam String status) {
        reportServicePort.addStatusToReport(orderId,status);
        ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_SUMMARY, description = AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_UNAUTHORIZED_DESCRIPTION)
    })
    @PutMapping(AdaptersConstants.ASSIGN_EMPLOYEE_ENDPOINT_URL)
    public ResponseEntity<Void> assignEmployee(@PathVariable Long employeeId, @PathVariable Long orderId, @RequestParam String status){
        reportServicePort.assignEmployee(employeeId, orderId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = AdaptersConstants.GENERATE_REPORT_ENDPOINT_SUMMARY, description = AdaptersConstants.GENERATE_REPORT_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.GENERATE_REPORT_ENDPOINT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.GENERATE_REPORT_ENDPOINT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.GENERATE_REPORT_ENDPOINT_UNAUTHORIZED_DESCRIPTION)
    })
    @GetMapping(AdaptersConstants.GENERATE_REPORT_ENDPOINT_URL)
    public ResponseEntity<String> generateReport(){
        return ResponseEntity.ok(reportServicePort.generateReport());
    }

    @Operation(summary = AdaptersConstants.GET_ALL_ORDERS_ENDPOINT_SUMMARY, description = AdaptersConstants.GET_ALL_ORDERS_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.GET_ALL_ORDERS_ENDPOINT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.GET_ALL_ORDERS_ENDPOINT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.GET_ALL_ORDERS_ENDPOINT_UNAUTHORIZED_DESCRIPTION)
    })
    @GetMapping(AdaptersConstants.GET_ALL_ORDERS_EFFICIENCY_ENDPOINT_URL)
    public ResponseEntity<List<OrderEfficiencyResponse>> getAllOrderEfficiencies() {
        return ResponseEntity.ok(
                reportResponseMapper.toOrderEfficiencyResponseList(reportServicePort.getAllOrderEfficiencies())
        );
    }

    @Operation(summary = AdaptersConstants.CALCULATE_EMPLOYEE_ENDPOINT_SUMMARY, description = AdaptersConstants.CALCULATE_EMPLOYEE_ENDPOINT_DESCRIPTION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = AdaptersConstants.OK, description = AdaptersConstants.CALCULATE_EMPLOYEE_ENDPOINT_OK_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.BAD_REQUEST, description = AdaptersConstants.CALCULATE_EMPLOYEE_ENDPOINT_BAD_REQUEST_DESCRIPTION),
            @ApiResponse(responseCode = AdaptersConstants.UNAUTHORIZED, description = AdaptersConstants.CALCULATE_EMPLOYEE_ENDPOINT_UNAUTHORIZED_DESCRIPTION)
    })
    @GetMapping(AdaptersConstants.CALCULATE_EMPLOYEE_RANKING_ENDPOINT_URL)
    public ResponseEntity<List<ReportResponse>> calculateEmployeeRanking() {
        return ResponseEntity.ok(reportResponseMapper.toReportResponseList(reportServicePort.calculateEmployeeRanking()));
    }
}
