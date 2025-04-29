package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<SupportRequest, Long> {
}
