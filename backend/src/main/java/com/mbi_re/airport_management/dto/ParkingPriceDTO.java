package com.mbi_re.airport_management.dto;

public class ParkingPriceDTO {
    private int entryHour;
    private int entryMinute;
    private int exitHour;
    private int exitMinute;
    private String tenantId;

    public ParkingPriceDTO() {}

    public ParkingPriceDTO(int entryHour, int entryMinute, int exitHour, int exitMinute, String tenantId) {
        this.entryHour = entryHour;
        this.entryMinute = entryMinute;
        this.exitHour = exitHour;
        this.exitMinute = exitMinute;
        this.tenantId = tenantId;
    }

    public int getEntryHour() {
        return entryHour;
    }

    public void setEntryHour(int entryHour) {
        this.entryHour = entryHour;
    }

    public int getEntryMinute() {
        return entryMinute;
    }

    public void setEntryMinute(int entryMinute) {
        this.entryMinute = entryMinute;
    }

    public int getExitHour() {
        return exitHour;
    }

    public void setExitHour(int exitHour) {
        this.exitHour = exitHour;
    }

    public int getExitMinute() {
        return exitMinute;
    }

    public void setExitMinute(int exitMinute) {
        this.exitMinute = exitMinute;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
