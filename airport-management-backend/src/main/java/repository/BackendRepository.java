package repository;


import model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackendRepository extends JpaRepository<Flight, Long> {
    
}

