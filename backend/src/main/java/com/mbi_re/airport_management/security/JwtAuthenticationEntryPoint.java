package com.mbi_re.airport_management.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom entry point for handling unauthorized access in JWT-based authentication.
 * <p>
 * This component is invoked when a user attempts to access a secured REST endpoint
 * without proper authentication credentials (e.g., missing or invalid JWT token).
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Sends a 401 Unauthorized response when authentication fails.
     *
     * @param request       the {@link HttpServletRequest} that resulted in an {@link AuthenticationException}
     * @param response      the {@link HttpServletResponse} to send the 401 error to
     * @param authException the exception that caused the authentication to fail
     * @throws IOException if sending the error response fails
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or missing token");
    }
}
