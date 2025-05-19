package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminalRepository extends JpaRepository<Terminal, Long> {
}
