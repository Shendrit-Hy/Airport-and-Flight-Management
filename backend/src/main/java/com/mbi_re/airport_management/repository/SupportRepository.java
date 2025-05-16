package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    Optional<Support> findByTicketId(String ticketId);
}
