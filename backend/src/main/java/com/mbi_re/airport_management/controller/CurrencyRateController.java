package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.model.CurrencyRate;
import com.mbi_re.airport_management.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/currency-rates")
public class CurrencyRateController {

    @Autowired
    private CurrencyRateRepository repository;

    @PostMapping
    public CurrencyRate addRate(@RequestBody CurrencyRate rate) {
        return repository.save(rate);
    }

    @PutMapping("/{code}")
    public CurrencyRate updateRate(@PathVariable String code, @RequestBody CurrencyRate rate) {
        rate.setCode(code);
        return repository.save(rate);
    }

    @GetMapping
    public List<CurrencyRate> getAllRates() {
        return repository.findAll();
    }

    @DeleteMapping("/{code}")
    public void deleteRate(@PathVariable String code) {
        repository.deleteById(code);
    }
}