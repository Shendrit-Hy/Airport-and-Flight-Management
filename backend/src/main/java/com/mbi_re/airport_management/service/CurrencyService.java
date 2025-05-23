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
     * Retrieves all available currencies supported by the JVM combined with
     * any custom currencies added at runtime.
     * <p>
     * This list is based on ISO 4217 standard and extended with user-added currencies.
     * The results are cached under "availableCurrencies" to improve performance.
     *
     * @return a list of {@link CurrencyDTO} representing all available currencies,
     *         including JVM standard and custom-added ones.
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
     * Adds a new currency to the in-memory custom currency list.
     * <p>
     * This method does not persist the currency globally or in any database;
     * it is intended for temporary or tenant-specific additions.
     *
     * @param currencyDTO the currency data transfer object to add
     * @return the added {@link CurrencyDTO} instance
     */
    public CurrencyDTO addCurrency(CurrencyDTO currencyDTO) {
        customCurrencies.put(currencyDTO.getCode(), currencyDTO);
        return currencyDTO;
    }

    /**
     * Removes a currency from the in-memory custom currency list by its currency code.
     * <p>
     * This operation does not affect the standard JVM currencies.
     *
     * @param code the currency code (ISO 4217) of the custom currency to delete
     */
    public void deleteCurrency(String code) {
        customCurrencies.remove(code);
    }
}
