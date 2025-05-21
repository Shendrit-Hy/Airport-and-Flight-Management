package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.service.PaymentService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing payment-related operations.
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "Handles payment operations per tenant.")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Retrieves all payments for the given tenant.
     *
     * @param tenantId tenant identifier from the request header
     * @return list of payment records
     */
    @Operation(
            summary = "Get payments",
            description = "Retrieves all payments for the specified tenant via X-Tenant-ID. Requires USER or ADMIN role."
    )
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<PaymentDTO> getPayments(
            @Parameter(description = "Tenant ID from X-Tenant-ID header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return paymentService.getPaymentsByTenant(tenantId);
    }

    /**
     * Saves a new payment for the given tenant.
     *
     * @param paymentDTO payment data
     * @param tenantId   tenant identifier from the request header
     * @return saved payment record
     */
    @Operation(
            summary = "Save payment",
            description = "Creates a new payment entry for the specified tenant. Requires ADMIN role."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PaymentDTO savePayment(
            @Parameter(description = "Payment information") @RequestBody PaymentDTO paymentDTO,
            @Parameter(description = "Tenant ID from X-Tenant-ID header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        paymentDTO.setTenantId(tenantId);
        return paymentService.savePayment(paymentDTO);
    }

    /**
     * Deletes a payment by reference for the given tenant.
     *
     * @param reference unique payment reference
     * @param tenantId  tenant identifier from the request header
     */
    @Operation(
            summary = "Delete payment",
            description = "Deletes a payment record by reference for the specified tenant. Requires ADMIN role."
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reference}")
    public void deletePayment(
            @Parameter(description = "Payment reference ID") @PathVariable String reference,
            @Parameter(description = "Tenant ID from X-Tenant-ID header") @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        paymentService.deletePaymentByReference(reference, tenantId);
    }
}
