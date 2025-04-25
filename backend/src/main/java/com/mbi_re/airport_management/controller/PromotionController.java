package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.PromotionDTO;
import com.mbi_re.airport_management.model.Promotion;
import com.mbi_re.airport_management.service.PromotionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@CrossOrigin
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

    @PostMapping
    public Promotion createPromotion(@RequestBody PromotionDTO dto) {
        return service.addPromotion(dto);
    }

    @GetMapping
    public List<Promotion> getAllPromotions() {
        return service.getAllPromotions();
    }
}

