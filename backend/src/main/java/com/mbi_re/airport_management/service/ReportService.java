package com.mbi_re.airport_management.service;
import com.mbi_re.airport_management.dto.ReportDTO;
import com.mbi_re.airport_management.repository.UserRepository;
import com.mbi_re.airport_management.repository.SupportRepository;
import com.mbi_re.airport_management.repository.AirportRepository;
import org.springframework.stereotype.Service;
@Service
public class ReportService {
    private final UserRepository userRepository;
    private final SupportRepository supportRepository;
    private final AirportRepository airportRepository;

    public ReportService(UserRepository userRepository,
                         SupportRepository supportRepository,
                         AirportRepository airportRepository) {
        this.userRepository = userRepository;
        this.supportRepository = supportRepository;
        this.airportRepository = airportRepository;
    }

    public ReportDTO generateReport() {
        ReportDTO report = new ReportDTO();
        report.setTotalUsers(userRepository.count());
        report.setTotalSupportRequests(supportRepository.count());
        report.setTotalAirports(airportRepository.count());
        return report;
    }
}
