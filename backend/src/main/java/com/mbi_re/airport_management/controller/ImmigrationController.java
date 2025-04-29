package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.ImmigrationDTO;
import com.mbi_re.airport_management.service.ImmigrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/immigration")
public class ImmigrationController {

    @Autowired
    private ImmigrationService service;

    @GetMapping
    public List<ImmigrationDTO> getAll() {
        return service.getAll();
    }
}
