package com.mbi_re.airport_management.repository;

import com.mbi_re.airport_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

