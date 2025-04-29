package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.BaggageDTO;
import com.mbi_re.airport_management.service.BaggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/baggage")
public class BaggageController {

    @Autowired
    private BaggageService baggageService;

    @PostMapping
    public ResponseEntity<?> createBaggage(@RequestBody BaggageDTO baggageDTO) {
        BaggageDTO created = baggageService.create(baggageDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }


    @GetMapping
    public List<BaggageDTO> getAllBaggage() {
        return baggageService.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<BaggageDTO> getBaggageById(@PathVariable Long id) {
        Optional<BaggageDTO> baggage = baggageService.getById(id);
        return baggage.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @PutMapping("/{id}")
    public ResponseEntity<BaggageDTO> updateBaggage(@PathVariable Long id, @RequestBody BaggageDTO baggageDTO) {
        Optional<BaggageDTO> updated = baggageService.update(id, baggageDTO);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBaggage(@PathVariable Long id) {
        boolean deleted = baggageService.delete(id);
        if (deleted) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

