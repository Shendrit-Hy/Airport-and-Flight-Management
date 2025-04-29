package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
