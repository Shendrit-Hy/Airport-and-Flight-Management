package com.mbi_re.airport_management.repository;


import com.mbi_re.airport_management.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findAllByTenantId(String tenantId);
}

