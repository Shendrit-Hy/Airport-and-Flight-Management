package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.dto.CustomsDTO;
import com.mbi_re.airport_management.service.CustomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customs")
public class CustomsController {

    @Autowired
    private CustomsService service;

    @PostMapping
    public CustomsDTO create(@RequestBody CustomsDTO dto) {
        return service.create(dto);
    }
}
