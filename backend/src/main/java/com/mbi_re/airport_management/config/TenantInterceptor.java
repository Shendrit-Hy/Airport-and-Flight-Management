package com.mbi_re.airport_management.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor to extract and validate the tenant ID from incoming HTTP requests.
 * <p>
 * This interceptor parses the tenant identifier from the request's host name,
 * typically expecting a subdomain (e.g., tenant1.example.com).
 * It optionally compares the extracted tenant ID with the "X-Tenant-ID" header to ensure consistency,
 * responding with a 403 Forbidden status if there's a mismatch.
 * <p>
 * On successful validation, the tenant ID is stored in {@link TenantContext} for downstream usage.
 */
@Component
public class TenantInterceptor implements HandlerInterceptor {

    /**
     * Extracts the tenant ID from the host name in the HTTP request.
     * <p>
     * Assumes the tenant ID is the first segment of the hostname (subdomain).
     * For example, for "tenant1.example.com", returns "tenant1".
     * If no subdomain is found (e.g., "localhost" or "example.com"), returns "default".
     *
     * @param request the HTTP servlet request
     * @return the extracted tenant ID or "default" if none found
     */
    private String extractTenantIdFromHost(HttpServletRequest request) {
        String host = request.getServerName(); // e.g., tenant1.example.com
        String[] parts = host.split("\\.");
        return (parts.length > 2) ? parts[0] : "default"; // fallback for dev/local
    }

    /**
     * Intercepts the request before it reaches the controller.
     * <p>
     * Extracts the tenant ID from the host, optionally compares it with the "X-Tenant-ID" header,
     * and if they mismatch, rejects the request with HTTP 403.
     * Otherwise, sets the tenant ID in the {@link TenantContext}.
     *
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param handler  chosen handler to execute, for type and/or instance evaluation
     * @return {@code true} to continue processing the request; {@code false} to abort
     * @throws Exception if any error occurs during processing
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantFromHost = extractTenantIdFromHost(request);

        // Optional: only use this in dev to compare with X-Tenant-ID
        String tenantFromHeader = request.getHeader("X-Tenant-ID");
        if (tenantFromHeader != null && !tenantFromHeader.equals(tenantFromHost)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Tenant ID mismatch with host");
            return false;
        }

        // Set trusted tenant ID into context
        TenantContext.setTenantId(tenantFromHost);
        return true;
    }
}
