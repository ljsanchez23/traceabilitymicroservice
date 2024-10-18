package com.foodcourt.TraceabilityMicroservice.domain.exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(String message){
        super(message);
    }
}
