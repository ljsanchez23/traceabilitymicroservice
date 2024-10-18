package com.foodcourt.TraceabilityMicroservice.configuration.security.jwt.filters;

import com.foodcourt.TraceabilityMicroservice.configuration.security.jwt.JwtValidate;
import com.foodcourt.TraceabilityMicroservice.configuration.security.util.SecurityConstants;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {

        try {
            if (JwtValidate.tokenExists(request)) {
                Claims claims = JwtValidate.JwtValidation(request);

                if (claims != null) {
                    String username = claims.getSubject();
                    Collection<? extends GrantedAuthority> authorities = getAuthorities(claims);

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (io.jsonwebtoken.JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(SecurityConstants.INVALID_JWT_SIGNATURE);
            response.getWriter().flush();
            return;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(SecurityConstants.AUTHENTICATION_FAILED);
            response.getWriter().flush();
            return;
        }

        chain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Claims claims) {
        Object authoritiesObject = claims.get(SecurityConstants.JWT_AUTHORITY);
        if (authoritiesObject instanceof List<?>) {
            @SuppressWarnings(SecurityConstants.UNCHECKED)
            List<String> roles = (List<String>) authoritiesObject;
            return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
        } else if (authoritiesObject instanceof String role) {
            return Collections.singletonList(new SimpleGrantedAuthority(role));
        } else {
            return Collections.emptyList();
        }
    }
}
