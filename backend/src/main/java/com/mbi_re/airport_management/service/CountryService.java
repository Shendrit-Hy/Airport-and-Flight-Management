package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CountryDTO;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(c -> new CountryDTO(c.getId(), c.getName()))
                .collect(Collectors.toList());
    }

    public CountryDTO createCountry(CountryDTO dto) {
        if (countryRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Country already exists!");
        }

        Country country = new Country();
        country.setName(dto.getName());

        Country saved = countryRepository.save(country);
        return new CountryDTO(saved.getId(), saved.getName());
    }
}
