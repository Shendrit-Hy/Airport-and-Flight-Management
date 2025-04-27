package com.mbi_re.airport_management.dto;

public class ReportDTO {
    private long totalUsers;
    private long totalSupportRequests;
    private long totalAirports;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalSupportRequests() {
        return totalSupportRequests;
    }

    public void setTotalSupportRequests(long totalSupportRequests) {
        this.totalSupportRequests = totalSupportRequests;
    }

    public long getTotalAirports() {
        return totalAirports;
    }

    public void setTotalAirports(long totalAirports) {
        this.totalAirports = totalAirports;
    }
}
