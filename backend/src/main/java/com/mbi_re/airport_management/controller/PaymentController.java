package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.service.PaymentService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing payment-related operations.
 * <p>
 * All operations are tenant-scoped and require tenant validation.
 * Access is restricted based on user roles (USER, ADMIN).
 * </p>
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "Handles payment operations per tenant.")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Retrieves all payments for the specified tenant.
     *
     * <p>Requires USER or ADMIN role.</p>
     *
     * @param tenantId the tenant identifier extracted from the "X-Tenant-ID" request header
     * @return list of payment records belonging to the tenant
     */
    @Operation(
            summary = "Get payments",
            description = "Retrieves all payments for the specified tenant via X-Tenant-ID. Requires USER or ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of payments"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant")
    })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<PaymentDTO> getPayments(
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        return paymentService.getPaymentsByTenant(tenantId);
    }

    /**
     * Saves a new payment for the specified tenant.
     *
     * <p>Automatically sets payment status to "PAID". Requires ADMIN role.</p>
     *
     * @param paymentDTO the payment information to be saved
     * @param tenantId   the tenant identifier extracted from the "X-Tenant-ID" request header
     * @return the saved PaymentDTO with updated information
     */
    @Operation(
            summary = "Save payment",
            description = "Creates a new payment entry for the specified tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment saved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant"),
            @ApiResponse(responseCode = "400", description = "Invalid payment data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PaymentDTO savePayment(
            @Parameter(description = "Payment information", required = true)
            @RequestBody PaymentDTO paymentDTO,
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        paymentDTO.setTenantId(tenantId);
        paymentDTO.setStatus("PAID");
        return paymentService.savePayment(paymentDTO);
    }

    /**
     * Deletes a payment identified by its reference for the specified tenant.
     *
     * <p>Requires ADMIN role. Performs deletion transactionally.</p>
     *
     * @param reference unique payment reference identifier
     * @param tenantId  the tenant identifier extracted from the "X-Tenant-ID" request header
     */
    @Operation(
            summary = "Delete payment",
            description = "Deletes a payment record by reference for the specified tenant. Requires ADMIN role."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - unauthorized or invalid tenant"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/{reference}")
    public void deletePayment(
            @Parameter(description = "Payment reference ID", required = true)
            @PathVariable String reference,
            @Parameter(description = "Tenant ID from X-Tenant-ID header", required = true)
            @RequestHeader("X-Tenant-ID") String tenantId) {
        TenantUtil.validateTenant(tenantId);
        paymentService.deletePaymentByReference(reference, tenantId);
    }
}
