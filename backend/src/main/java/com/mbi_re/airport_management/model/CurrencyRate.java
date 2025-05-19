package com.mbi_re.airport_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CurrencyRate {

    @Id
    private String code; // e.g., "USD", "EUR", "GBP"

    private double rateToUSD; // e.g., 1.0 for USD, 0.93 for EUR

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

    public void setRateToUSD(double rateToUSD) {
        this.rateToUSD = rateToUSD;
    }
}