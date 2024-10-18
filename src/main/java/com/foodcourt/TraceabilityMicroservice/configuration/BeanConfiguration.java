package com.foodcourt.TraceabilityMicroservice.configuration;

import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.adapter.ReportAdapter;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.mapper.IReportDocumentMapper;
import com.foodcourt.TraceabilityMicroservice.adapters.driven.mongodb.repository.IReportRepository;
import com.foodcourt.TraceabilityMicroservice.domain.api.IReportServicePort;
import com.foodcourt.TraceabilityMicroservice.domain.api.usecase.ReportUseCase;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IAuthenticationPort;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IReportPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IAuthenticationPort authenticationPort;
    private final IReportRepository reportRepository;
    private final IReportDocumentMapper reportDocumentMapper;

    @Bean
    public IReportPersistencePort reportPersistencePort(){
        return new ReportAdapter(reportRepository, reportDocumentMapper);
    }

    @Bean
    public IReportServicePort reportServicePort(){
        return new ReportUseCase(reportPersistencePort(), authenticationPort);
    }
}
