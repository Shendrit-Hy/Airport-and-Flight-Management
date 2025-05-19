package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CurrencyDTO;
import com.mbi_re.airport_management.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }
}

