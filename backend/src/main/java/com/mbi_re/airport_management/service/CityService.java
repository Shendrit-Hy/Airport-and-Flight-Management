package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.CityDTO;
import com.mbi_re.airport_management.model.City;
import com.mbi_re.airport_management.model.Country;
import com.mbi_re.airport_management.repository.CityRepository;
import com.mbi_re.airport_management.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(c -> new CityDTO(c.getId(), c.getName(), c.getCountry().getId()))
                .collect(Collectors.toList());
    }

    public CityDTO createCity(CityDTO dto) {
        Country country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));

        City city = new City();
        city.setName(dto.getName());
        city.setCountry(country);

        City saved = cityRepository.save(city);
        return new CityDTO(saved.getId(), saved.getName(), saved.getCountry().getId());
    }

    public List<CityDTO> getCitiesByCountry(Long countryId) {
        return cityRepository.findByCountryId(countryId).stream()
                .map(c -> new CityDTO(c.getId(), c.getName(), c.getCountry().getId()))
                .collect(Collectors.toList());
    }
}
