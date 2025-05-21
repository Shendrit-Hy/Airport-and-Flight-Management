package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling payment operations for tenants.
 */
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    /**
     * Retrieves all payment records for a given tenant.
     * Uses caching to optimize repeated fetches per tenant.
     *
     * @param tenantId the tenant ID
     * @return list of PaymentDTOs
     */
    @Cacheable(value = "payments", key = "#tenantId")
    public List<PaymentDTO> getPaymentsByTenant(String tenantId) {
        return paymentRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a payment by its reference for the given tenant.
     * Evicts the tenant's cache to reflect changes immediately.
     *
     * @param reference the payment reference
     * @param tenantId  the tenant ID
     */
    @CacheEvict(value = "payments", key = "#tenantId")
    public void deletePaymentByReference(String reference, String tenantId) {
        Payment payment = paymentRepository.findByReferenceAndTenantId(reference, tenantId);
        if (payment != null) {
            paymentRepository.delete(payment);
        }
    }

    /**
     * Saves a new payment for the given tenant.
     * Evicts the tenant's cache to maintain consistency.
     *
     * @param dto the PaymentDTO with payment details
     * @return the saved PaymentDTO
     */
    @CacheEvict(value = "payments", key = "#dto.tenantId")
    public PaymentDTO savePayment(PaymentDTO dto) {
        Payment payment = toEntity(dto);
        payment.setPaymentTime(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        return toDTO(saved);
    }

    /**
     * Converts a Payment entity to a DTO.
     *
     * @param payment the Payment entity
     * @return the corresponding PaymentDTO
     */
    private PaymentDTO toDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setMethod(payment.getMethod());
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setReference(payment.getReference());
        dto.setTenantId(payment.getTenantId());
        return dto;
    }

    /**
     * Converts a PaymentDTO to an entity.
     *
     * @param dto the PaymentDTO
     * @return the corresponding Payment entity
     */
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
