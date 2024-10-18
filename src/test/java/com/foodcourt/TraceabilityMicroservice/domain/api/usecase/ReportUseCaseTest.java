package com.foodcourt.TraceabilityMicroservice.domain.api.usecase;

import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IAuthenticationPort;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import com.foodcourt.TraceabilityMicroservice.domain.util.Constants;
import com.foodcourt.TraceabilityMicroservice.util.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestComponent;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportUseCaseTest {


    @Mock
    private IReportPersistencePort reportPersistencePort;

    @Mock
    private IAuthenticationPort authenticationPort;

    @InjectMocks
    private ReportUseCase reportUseCase;

    @Test
    @DisplayName(TestConstants.SHOULD_START_REPORT_WHEN_VALID_DATA)
    void shouldStartReportWithGivenData() {
        Long customerId = TestConstants.CUSTOMER_ID;
        Long orderId = TestConstants.ORDER_ID;
        String status = TestConstants.PENDING_STATUS;

        reportUseCase.startReport(customerId, orderId, status);

        ArgumentCaptor<Report> reportCaptor = ArgumentCaptor.forClass(Report.class);
        verify(reportPersistencePort).saveReport(reportCaptor.capture());

        Report savedReport = reportCaptor.getValue();
        assertEquals(orderId, savedReport.getOrderId());
        assertEquals(customerId, savedReport.getCustomerId());
        assertEquals(TestConstants.PENDING_STATUS, savedReport.getStatusHistory().keySet().iterator().next());
    }

    @Test
    @DisplayName(TestConstants.SHOULD_GENERATE_REPORT_FOR_AUTH_USER)
    void shouldGenerateReportForAuthenticatedUser() {
        Long customerId = TestConstants.CUSTOMER_ID;
        List<Report> reports = List.of(new Report(TestConstants.ORDER_ID, null, customerId, LocalDateTime.now(), new HashMap<>()));

        when(authenticationPort.getAuthenticatedUserId()).thenReturn(customerId);
        when(reportPersistencePort.getReportsByCustomerId(customerId)).thenReturn(reports);

        String result = reportUseCase.generateReport();

        assertNotNull(result);
        verify(reportPersistencePort).getReportsByCustomerId(customerId);
    }

    @Test
    @DisplayName(TestConstants.SHOULD_THROW_EXCEPTION_WHEN_NO_REPORT_IS_FOUND)
    void shouldThrowExceptionWhenReportsNotFound() {
        Long customerId = TestConstants.CUSTOMER_ID;

        when(authenticationPort.getAuthenticatedUserId()).thenReturn(customerId);
        when(reportPersistencePort.getReportsByCustomerId(customerId)).thenReturn(Collections.emptyList());

        assertThrows(ReportNotFoundException.class, () -> reportUseCase.generateReport());
    }

    @Test
    @DisplayName(TestConstants.SHOULD_ADD_STATUS_TO_EXISTING_REPORT)
    void shouldAddStatusToExistingReport() {
        Long orderId = 100L;
        Report report = new Report(orderId, null, 1L, LocalDateTime.now(), new HashMap<>());

        when(reportPersistencePort.findReportByOrderId(orderId)).thenReturn(report);

        reportUseCase.addStatusToReport(orderId, TestConstants.READY_STATUS);

        verify(reportPersistencePort).saveReport(report);
        assertTrue(report.getStatusHistory().containsKey(TestConstants.READY_STATUS));
    }

    @Test
    @DisplayName(TestConstants.SHOULD_THROW_EXCEPTION_WHEN_REPORT_NOT_FOUND)
    void shouldThrowExceptionWhenReportNotFoundForAddingStatus() {
        Long orderId = TestConstants.ORDER_ID;

        when(reportPersistencePort.findReportByOrderId(orderId)).thenReturn(null);

        assertThrows(ReportNotFoundException.class, () -> reportUseCase.addStatusToReport(orderId, TestConstants.READY_STATUS));
    }

    @Test
    @DisplayName(TestConstants.SHOULD_ASSIGN_EMPLOYEE_TO_AN_ORDER)
    void shouldAssignEmployeeToOrderAndAddStatus() {
        Long orderId = TestConstants.ORDER_ID;
        Long employeeId = TestConstants.EMPLOYEE_ID;
        Report report = new Report(orderId, null, TestConstants.CUSTOMER_ID, LocalDateTime.now(), new HashMap<>());

        when(reportPersistencePort.findReportByOrderId(orderId)).thenReturn(report);

        reportUseCase.assignEmployee(employeeId, orderId, TestConstants.ASSIGNED_STATUS);

        verify(reportPersistencePort).saveReport(report);
        assertEquals(employeeId, report.getEmployeeId());
        assertTrue(report.getStatusHistory().containsKey(TestConstants.ASSIGNED_STATUS));
    }
}
