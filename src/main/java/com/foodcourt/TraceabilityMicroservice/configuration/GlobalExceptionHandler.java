package com.foodcourt.TraceabilityMicroservice.configuration;

import com.foodcourt.TraceabilityMicroservice.configuration.util.ConfigurationConstants;
import com.foodcourt.TraceabilityMicroservice.configuration.util.ErrorResponse;
import com.foodcourt.TraceabilityMicroservice.domain.exception.ReportNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(ReportNotFoundException ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestUri = (request != null) ? request.getRequestURI() : ConfigurationConstants.DEFAULT_PATH;

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ConfigurationConstants.BAD_REQUEST,
                ex.getMessage(),
                requestUri
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }
}
