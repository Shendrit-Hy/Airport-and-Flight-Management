package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CurrencyDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Set;

@Service
public class CurrencyService {

    public List<CurrencyDTO> getAllCurrencies() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        List<CurrencyDTO> list = new ArrayList<>();

        for (Currency currency : currencies) {
            list.add(new CurrencyDTO(currency.getCurrencyCode(), currency.getDisplayName()));
        }

        return list;
    }
}

