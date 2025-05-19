package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Autowired
    private  PaymentService paymentService;

    @GetMapping
    public List<PaymentDTO> getPayments(@RequestHeader("X-Tenant-ID") String tenantId) {
        return paymentService.getPaymentsByTenant(tenantId);
    }


    @PostMapping
    public PaymentDTO savePayment(@RequestBody PaymentDTO paymentDTO,
                                  @RequestHeader("X-Tenant-ID") String tenantId) {
        paymentDTO.setTenantId(tenantId);
        return paymentService.savePayment(paymentDTO);
    }

    @DeleteMapping("/{reference}")
    public void deletePayment(@PathVariable String reference,
                              @RequestHeader("X-Tenant-ID") String tenantId) {
        paymentService.deletePaymentByReference(reference, tenantId);
    }

}
