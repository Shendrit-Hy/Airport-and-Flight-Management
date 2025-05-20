package com.mbi_re.airport_management.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private String extractTenantIdFromHost(HttpServletRequest request) {
        String host = request.getServerName(); // e.g., tenant1.example.com
        String[] parts = host.split("\\.");
        return (parts.length > 2) ? parts[0] : "default"; // fallback for dev/local
    }

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


