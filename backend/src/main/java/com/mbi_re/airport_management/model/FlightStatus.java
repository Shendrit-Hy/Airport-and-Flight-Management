package com.mbi_re.airport_management.model;

public enum FlightStatus {
    SCHEDULED("Scheduled"),
    BOARDING("Boarding"),
    DEPARTED("Departed"),
    IN_AIR("In Air"),
    LANDED("Landed"),
    DELAYED("Delayed"),
    CANCELLED("Cancelled"),
    DIVERTED("Diverted"),
    RETURNED_TO_GATE("Returned to Gate"),
    UNKNOWN("Unknown");

    private final String displayName;

    FlightStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

