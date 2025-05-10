package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.config.TenantContext;
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
        String tenantId = TenantContext.getTenantId();

        Weather weather = weatherRepository.findByTenantId(tenantId).orElseGet(() -> {
            Weather w = new Weather();
            w.setTemperature("18°C");
            w.setCondition("Sunny");
            w.setRunwayStatus("Runway clear and dry");
            w.setTenantId(tenantId);
            return weatherRepository.save(w);
        });

        WeatherDTO dto = new WeatherDTO();
        dto.setTemperature(weather.getTemperature());
        dto.setCondition(weather.getCondition());
        dto.setRunwayStatus(weather.getRunwayStatus());
        dto.setTenantId(weather.getTenantId());
        return dto;
    }
}
