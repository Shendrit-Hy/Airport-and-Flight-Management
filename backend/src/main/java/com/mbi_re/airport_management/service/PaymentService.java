package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Retrieves all payment records associated with the specified tenant.
     * Uses caching to optimize performance for repeated requests.
     *
     * @param tenantId the tenant identifier to filter payments
     * @return a list of PaymentDTO representing payments for the tenant
     */
    @Cacheable(value = "payments", key = "#tenantId")
    public List<PaymentDTO> getPaymentsByTenant(String tenantId) {
        return paymentRepository.findAllByTenantId(tenantId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a payment record identified by its reference and tenant ID.
     * Evicts the cached payments for the tenant to ensure cache consistency.
     *
     * @param reference the unique payment reference identifier
     * @param tenantId  the tenant identifier for authorization
     */
    @CacheEvict(value = "payments", key = "#tenantId")
    public void deletePaymentByReference(String reference, String tenantId) {
        Payment payment = paymentRepository.findByReferenceAndTenantId(reference, tenantId);
        if (payment != null) {
            paymentRepository.delete(payment);
        }
    }

    /**
     * Saves a new payment record for the specified tenant.
     * Automatically sets the current time as the payment time.
     * Evicts the tenant's payment cache after saving to keep data fresh.
     *
     * @param dto the PaymentDTO containing payment details and tenant ID
     * @return the saved PaymentDTO with persisted details
     */
    @CacheEvict(value = "payments", key = "#dto.tenantId")
    public PaymentDTO savePayment(PaymentDTO dto) {
        Payment payment = toEntity(dto);
        payment.setPaymentTime(LocalDateTime.now());
        Payment saved = paymentRepository.save(payment);
        return toDTO(saved);
    }

    /**
     * Converts a Payment entity to its corresponding DTO.
     *
     * @param payment the Payment entity to convert
     * @return the PaymentDTO representation
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
     * Converts a PaymentDTO to its corresponding entity.
     *
     * @param dto the PaymentDTO to convert
     * @return the Payment entity representation
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
