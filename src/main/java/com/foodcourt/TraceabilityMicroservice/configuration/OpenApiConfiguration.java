package com.foodcourt.TraceabilityMicroservice.configuration;

import com.foodcourt.TraceabilityMicroservice.configuration.util.ConfigurationConstants;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Bean
    public OpenAPI UserMicroserviceAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title(ConfigurationConstants.APP_NAME)
                        .version(ConfigurationConstants.APP_VERSION)
                        .description(ConfigurationConstants.APP_DESCRIPTION));
    }
}
