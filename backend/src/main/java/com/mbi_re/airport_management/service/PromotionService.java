package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.PromotionDTO;
import com.mbi_re.airport_management.model.Promotion;
import com.mbi_re.airport_management.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    private final PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public Promotion addPromotion(PromotionDTO dto) {
        Promotion promo = Promotion.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .discountPercent(dto.getDiscountPercent())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(dto.isActive())
                .build();

        return repository.save(promo);
    }

    public List<Promotion> getAllPromotions() {
        return repository.findAll();
    }
}

