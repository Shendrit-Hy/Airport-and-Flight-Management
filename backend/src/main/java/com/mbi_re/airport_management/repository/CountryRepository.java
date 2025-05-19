package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByName(String name);
}
