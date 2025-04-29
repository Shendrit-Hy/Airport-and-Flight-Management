package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.WeatherDTO;
import com.mbi_re.airport_management.model.Weather;
import com.mbi_re.airport_management.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherDTO getWeather() {
        Weather weather = weatherRepository.findAll().stream().findFirst()
                .orElseGet(() -> {
                    Weather w = new Weather();
                    w.setTemperature("18°C");
                    w.setCondition("Sunny");
                    w.setRunwayStatus("Runway clear and dry");
                    return weatherRepository.save(w);
                });

        WeatherDTO dto = new WeatherDTO();
        dto.setTemperature(weather.getTemperature());
        dto.setCondition(weather.getCondition());
        dto.setRunwayStatus(weather.getRunwayStatus());
        return dto;
    }
}
