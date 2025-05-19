package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverterService {

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    public double convert(String from, String to, double value) {
        CurrencyRate fromRate = currencyRateRepository.findById(from.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Currency not found: " + from));
        CurrencyRate toRate = currencyRateRepository.findById(to.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Currency not found: " + to));

        double usdValue = value / fromRate.getRateToUSD();
        return usdValue * toRate.getRateToUSD();
    }
}