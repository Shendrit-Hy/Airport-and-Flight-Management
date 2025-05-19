package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.model.Language;
import com.mbi_re.airport_management.service.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping
    public List<Language> getLanguages() {
        return languageService.getAllLanguages();
    }
}
