package com.foodcourt.TraceabilityMicroservice.domain.api.usecase;

import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import com.foodcourt.TraceabilityMicroservice.domain.model.OrderEfficiency;
import com.foodcourt.TraceabilityMicroservice.domain.model.Report;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IAuthenticationPort;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import com.foodcourt.TraceabilityMicroservice.domain.util.Constants;
import com.foodcourt.TraceabilityMicroservice.util.TestConstants;
import com.foodcourt.TraceabilityMicroservice.util.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Long orderId = TestConstants.ORDER_ID;
        Report report = new Report(orderId, null, TestConstants.CUSTOMER_ID, LocalDateTime.now(), new HashMap<>());

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

    @Test
    @DisplayName(TestConstants.SHOULD_RETURN_EMPLOYEE_RANKING)
    void shouldCalculateEmployeeRankingCorrectly() {
        List<OrderEfficiency> efficiencies = List.of(
                new OrderEfficiency(TestConstants.FIRST_ORDER_ID, 1L, Duration.ofMinutes(10)),
                new OrderEfficiency(TestConstants.SECOND_ORDER_ID, 1L, Duration.ofMinutes(20)),
                new OrderEfficiency(TestConstants.THIRD_ORDER_EFFICIENCY, 2L, Duration.ofMinutes(15))
        );

        when(reportPersistencePort.calculateOrderEfficiencies()).thenReturn(efficiencies);

        Map<Long, Double> ranking = reportUseCase.calculateEmployeeRanking();

        assertEquals(15.0, ranking.get(1L));
        assertEquals(15.0, ranking.get(2L));
        verify(reportPersistencePort).calculateOrderEfficiencies();
    }

    @Test
    @DisplayName(TestConstants.SHOULD_RETURN_EFFICIENCY_LIST)
    void shouldReturnAllOrderEfficienciesCorrectly() {
        Map<String, LocalDateTime> statusHistory1 = Map.of(
                Constants.PENDING_STATUS, LocalDateTime.now().minusMinutes(20),
                Constants.DELIVERED_STATUS, LocalDateTime.now()
        );

        Map<String, LocalDateTime> statusHistory2 = Map.of(
                Constants.PENDING_STATUS, LocalDateTime.now().minusMinutes(15),
                Constants.DELIVERED_STATUS, LocalDateTime.now()
        );

        List<Report> reports = List.of(
                TestDataFactory.createReport(TestConstants.FIRST_ORDER_ID, 1L, statusHistory1),
                TestDataFactory.createReport(TestConstants.SECOND_ORDER_ID, 2L, statusHistory2)
        );

        when(reportPersistencePort.getAllReports()).thenReturn(reports);

        List<OrderEfficiency> efficiencies = reportUseCase.getAllOrderEfficiencies();

        assertEquals(2, efficiencies.size());

        assertTrue(Math.abs(efficiencies.get(0).getTimeTaken().toSeconds() - TestConstants.FIRST_TIME) < 1,
                TestConstants.FIST_TRY_EXPECTED + efficiencies.get(0).getTimeTaken().toSeconds());
        assertTrue(Math.abs(efficiencies.get(1).getTimeTaken().toSeconds() - TestConstants.SECOND_TIME) < 1,
                TestConstants.SECOND_TRY_EXPECTED + efficiencies.get(1).getTimeTaken().toSeconds());
    }
}


