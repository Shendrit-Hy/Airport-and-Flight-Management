package com.mbi_re.airport_management.service;

import com.mbi_re.airport_management.dto.LostItemDTO;
import com.mbi_re.airport_management.model.LostItem;
import com.mbi_re.airport_management.repository.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LostItemService {

    private final LostItemRepository repository;

    @Autowired
    public LostItemService(LostItemRepository repository) {
        this.repository = repository;
    }

    public LostItem reportLostItem(LostItemDTO dto) {
        LostItem item = LostItem.builder()
                .itemName(dto.getItemName())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .contactEmail(dto.getContactEmail())
                .reportedAt(LocalDateTime.now())
                .build();
        return repository.save(item);
    }

    public List<LostItem> getAllLostItems() {
        return repository.findAll();
    }
}
