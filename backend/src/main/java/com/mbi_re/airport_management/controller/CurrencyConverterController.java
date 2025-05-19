package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class CurrencyConverterController {

    @Autowired
    private CurrencyConverterService converterService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double value) {

        double result = converterService.convert(from, to, value);

        Map<String, Object> response = new HashMap<>();
        response.put("from", from);
        response.put("to", to);
        response.put("originalAmount", value);
        response.put("convertedAmount", result);

        return ResponseEntity.ok(response);
    }
}