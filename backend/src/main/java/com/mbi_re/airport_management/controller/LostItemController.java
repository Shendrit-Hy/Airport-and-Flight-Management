package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.LostItemDTO;
import com.mbi_re.airport_management.model.LostItem;
import com.mbi_re.airport_management.service.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lostfound")
public class LostItemController {

    private final LostItemService service;

    @Autowired
    public LostItemController(LostItemService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LostItem> reportLostItem(@RequestBody LostItemDTO dto) {
        return ResponseEntity.ok(service.reportLostItem(dto));
    }

    @GetMapping
    public ResponseEntity<List<LostItem>> getLostItems() {
        return ResponseEntity.ok(service.getAllLostItems());
    }
}
