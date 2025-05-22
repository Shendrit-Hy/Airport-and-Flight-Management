package com.mbi_re.airport_management.utils;

import com.mbi_re.airport_management.config.TenantContext;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@UtilityClass
public class TenantUtil {

    public static void validateTenant(String jwtTenantId) {
        String contextTenant = TenantContext.getTenantId();
        if (jwtTenantId == null || !jwtTenantId.equals(contextTenant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid tenant ID");
        }
    }

    public static void validateTenantFromContext() {
        String contextTenant = TenantContext.getTenantId();
        if (contextTenant == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Missing tenant context");
        }
    }

    public static String getCurrentTenant() {
        return TenantContext.getTenantId();
    }


}

