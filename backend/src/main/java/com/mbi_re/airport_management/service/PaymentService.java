package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<PaymentDTO> getPaymentsByTenant(String tenantId) {
        return paymentRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    public void deletePaymentByReference(String reference, String tenantId) {
        Payment payment = paymentRepository.findByReferenceAndTenantId(reference, tenantId);
        if (payment != null) {
            paymentRepository.delete(payment);
        }
    }


    public PaymentDTO savePayment(PaymentDTO dto) {
        Payment payment = toEntity(dto);
        System.out.println(dto.getStatus()+dto.getAmount());
        payment.setPaymentTime(LocalDateTime.now()); // set automatically on save
        Payment saved = paymentRepository.save(payment);
        return toDTO(saved);
    }

    private PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setMethod(payment.getMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setReference(payment.getReference());
        dto.setTenantId(payment.getTenantId());
        return dto;
    }

    private Payment toEntity(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setMethod(dto.getMethod());
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());
        payment.setReference(dto.getReference());
        payment.setTenantId(dto.getTenantId());
        return payment;
    }
}
