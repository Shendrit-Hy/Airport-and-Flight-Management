package com.mbi_re.airport_management.utils;

import com.mbi_re.airport_management.config.TenantContext;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
@UtilityClass
public class TenantUtil {
    public static void validateTenant(String tenantHeader) {
        String contextTenant = TenantContext.getTenantId();
        if (tenantHeader == null || !tenantHeader.equals(contextTenant)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid tenant ID");
        }
    }
}
