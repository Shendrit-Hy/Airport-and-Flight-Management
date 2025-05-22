package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CurrencyDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service providing currency-related operations.
 * It retrieves and manages the list of ISO 4217 currencies.
 * For simplicity, added/deleted currencies are tracked in-memory.
 */
@Service
public class CurrencyService {

    private final Map<String, CurrencyDTO> customCurrencies = new ConcurrentHashMap<>();

    /**
     * Retrieves all available currencies supported by the JVM and any custom ones added at runtime.
     * This list is based on ISO 4217 and extended with user-added currencies.
     * <p>
     * The result is cached to reduce computation overhead.
     *
     * @return a list of {@link CurrencyDTO} representing all available currencies.
     */
    @Cacheable("availableCurrencies")
    public List<CurrencyDTO> getAllCurrencies() {
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        List<CurrencyDTO> list = new ArrayList<>();

        for (Currency currency : currencies) {
            list.add(new CurrencyDTO(currency.getCurrencyCode(), currency.getDisplayName()));
        }

        list.addAll(customCurrencies.values()); // Include any tenant-added currencies
        return list;
    }

    /**
     * Adds a new currency to the in-memory custom list.
     * This does not persist the currency globally or in a database.
     * Used for demonstration or testing tenant-specific additions.
     *
     * @param currencyDTO the currency to add
     * @return the added {@link CurrencyDTO}
     */
    public CurrencyDTO addCurrency(CurrencyDTO currencyDTO) {
        customCurrencies.put(currencyDTO.getCode(), currencyDTO);
        return currencyDTO;
    }

    /**
     * Deletes a currency from the in-memory custom list by its currency code.
     * Does not affect standard JVM currencies.
     *
     * @param code the currency code to delete
     */
    public void deleteCurrency(String code) {
        customCurrencies.remove(code);
    }
}
