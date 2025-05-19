package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, String> {
}