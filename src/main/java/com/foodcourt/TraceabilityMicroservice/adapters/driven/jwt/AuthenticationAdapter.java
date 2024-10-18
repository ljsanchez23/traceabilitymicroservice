package com.foodcourt.TraceabilityMicroservice.adapters.driven.jwt;
import com.foodcourt.TraceabilityMicroservice.adapters.util.AdaptersConstants;
import com.foodcourt.TraceabilityMicroservice.configuration.security.jwt.JwtValidate;
import com.foodcourt.TraceabilityMicroservice.domain.spi.IAuthenticationPort;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class AuthenticationAdapter implements IAuthenticationPort {

    @Override
    public Long getAuthenticatedUserId() {
        Claims claims = JwtValidate.JwtValidation(Objects.requireNonNull(getCurrentHttpRequest()));
        return claims.get(AdaptersConstants.USER_ID_FROM_TOKEN, Long.class);
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return (attributes != null) ? attributes.getRequest() : null;
    }
}