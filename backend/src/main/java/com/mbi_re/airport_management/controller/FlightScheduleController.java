package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.FlightScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
public class FlightScheduleController {

    @Autowired
    private FlightScheduleService service;

    @PostMapping
    public ResponseEntity<FlightScheduleDTO> create(@RequestBody FlightScheduleDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<FlightScheduleDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightScheduleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightScheduleDTO> update(@PathVariable Long id, @RequestBody FlightScheduleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
