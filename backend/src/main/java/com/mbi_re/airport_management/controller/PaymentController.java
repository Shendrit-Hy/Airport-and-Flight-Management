package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PaymentDTO;
import com.mbi_re.airport_management.model.Payment;
import com.mbi_re.airport_management.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public Payment create(@RequestBody PaymentDTO dto) {
        return service.makePayment(dto);
    }

    @GetMapping
    public List<Payment> getAll() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getById(@PathVariable Long id) {
        return service.getPaymentById(id);
    }
}

