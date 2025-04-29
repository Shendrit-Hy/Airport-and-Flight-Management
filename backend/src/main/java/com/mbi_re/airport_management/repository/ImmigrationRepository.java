package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Immigration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImmigrationRepository extends JpaRepository<Immigration, Long> {}
