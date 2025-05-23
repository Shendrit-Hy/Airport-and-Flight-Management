package com.mbi_re.airport_management.utils;

import com.mbi_re.airport_management.config.TenantContext;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for tenant validation and retrieval operations.
 * Centralizes tenant-related checks and logic to ensure consistent multi-tenancy behavior.
 */
@UtilityClass
public class TenantUtil {

    /**
     * Validates that the tenant ID extracted from the JWT matches the tenant ID in the current context.
     *
     * @param jwtTenantId the tenant ID extracted from the JWT
     * @throws ResponseStatusException if the tenant ID is null or does not match the context
     */
    public static void validateTenant(String jwtTenantId) {
        String contextTenant = TenantContext.getTenantId();
        if (jwtTenantId == null || !jwtTenantId.equals(contextTenant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid tenant ID");
        }
    }

    /**
     * Validates that a tenant ID is present in the current context.
     *
     * @throws ResponseStatusException if the tenant ID is missing from the context
     */
    public static void validateTenantFromContext() {
        String contextTenant = TenantContext.getTenantId();
        if (contextTenant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing tenant context");
        }
    }

    /**
     * Retrieves the current tenant ID from the context.
     *
     * @return the current tenant ID, or {@code null} if not set
     */
    public static String getCurrentTenant() {
        return TenantContext.getTenantId();
    }
}
