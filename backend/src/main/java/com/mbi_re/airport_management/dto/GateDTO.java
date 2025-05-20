package com.mbi_re.airport_management.dto;

public class GateDTO {
    private String gateNumber;

    // Optional: tenantId, flightId nëse do i lidhësh
    private String tenantId;

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
