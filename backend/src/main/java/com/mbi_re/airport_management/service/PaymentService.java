package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment makePayment(PaymentDTO dto) {
        Payment payment = Payment.builder()
                .method(dto.getMethod())
                .amount(dto.getAmount())
                .status(dto.getStatus() != null ? dto.getStatus() : "PAID")
                .reference(dto.getReference())
                .paymentTime(LocalDateTime.now())
                .build();
        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

