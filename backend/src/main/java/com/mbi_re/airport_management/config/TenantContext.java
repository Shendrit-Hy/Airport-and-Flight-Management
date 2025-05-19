package com.mbi_re.airport_management.config;

public class TenantContext {
    private static final InheritableThreadLocal<String> currentTenant = new InheritableThreadLocal<>();

    public static void setTenantId(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static String getTenantId() {
        return currentTenant.get();
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }

    public static boolean hasTenant() {
        return currentTenant.get() != null;
    }

    public static void clear() {
        currentTenant.remove();
    }
}
