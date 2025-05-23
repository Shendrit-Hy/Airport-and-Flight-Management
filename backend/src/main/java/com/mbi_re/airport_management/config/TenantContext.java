package com.mbi_re.airport_management.config;

/**
 * TenantContext holds the tenant identifier for the current thread.
 * This is used to support multi-tenancy by associating data and operations
 * with a specific tenant in a thread-safe manner.
 * <p>
 * The tenant ID is stored using a {@link ThreadLocal} variable,
 * ensuring that each thread has its own isolated tenant context.
 */
public class TenantContext {

    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    /**
     * Sets the tenant ID for the current thread.
     * The tenant ID is converted to lowercase before storing.
     *
     * @param tenantId the tenant identifier to set
     */
    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId.toLowerCase());
    }

    /**
     * Retrieves the tenant ID associated with the current thread.
     *
     * @return the tenant ID, or {@code null} if none is set
     */
    public static String getTenantId() {
        return currentTenant.get();
    }

    /**
     * Clears the tenant ID for the current thread.
     * This should be called to prevent memory leaks after the tenant context is no longer needed.
     */
    public static void clear() {
        currentTenant.remove();
    }

    /**
     * Checks if a tenant ID is currently set for the thread.
     *
     * @return {@code true} if a tenant ID is set, {@code false} otherwise
     */
    public static boolean hasTenant() {
        return currentTenant.get() != null;
    }
}
