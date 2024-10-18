package com.foodcourt.TraceabilityMicroservice.configuration.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.TraceabilityMicroservice.configuration.util.ConfigurationConstants;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        String errorMessage = extractErrorMessage(response);

        if (errorMessage == null) {
            errorMessage = ConfigurationConstants.ERROR_HAS_OCCURRED;
        }
        switch (status) {
            case NOT_FOUND:
                return new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
            case BAD_REQUEST:
                return new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage);
            default:
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }

    private String extractErrorMessage(Response response) {
        try {
            if (response.body() != null) {
                String body = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(body);

                if (jsonNode.has(ConfigurationConstants.MESSAGE_FIELD)) {
                    return jsonNode.get(ConfigurationConstants.MESSAGE_FIELD).asText();
                } else {
                    return body;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

