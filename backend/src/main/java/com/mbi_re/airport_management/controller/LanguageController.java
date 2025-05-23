package com.mbi_re.airport_management.controller;

import com.mbi_re.airport_management.config.TenantContext;
import com.mbi_re.airport_management.model.Language;
import com.mbi_re.airport_management.service.LanguageService;
import com.mbi_re.airport_management.utils.TenantUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
@CrossOrigin
@Tag(name = "Languages", description = "Endpoints for managing supported languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    /*
     * Retrieve all available languages.
     *
     * Accessible to all users including unauthenticated ones.
     * Validates tenant from header if unauthenticated.
     *
     * @param request HTTP request (used to extract X-Tenant-ID)
     * @return list of language objects
     */
    @GetMapping
    @Operation(summary = "Get all languages", description = "Retrieve a list of all supported languages.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of languages returned successfully"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed")
    })
    public List<Language> getLanguages(HttpServletRequest request) {
        // For unauthenticated access, validate tenant via header
        if (!TenantContext.hasTenant()) {
            String tenantFromHeader = request.getHeader("X-Tenant-ID");
            if (tenantFromHeader == null) {
                throw new IllegalArgumentException("Missing X-Tenant-ID header");
            }
            TenantContext.setTenantId(tenantFromHeader);
        }
        TenantUtil.validateTenantFromContext();
        return languageService.getAllLanguages();
    }

    /**
     * Add a new language to the system.
     *
     * Accessible only to users with the ADMIN role.
     * Validates tenant from JWT.
     *
     * @param language language object to add
     * @param jwtTenantId tenant ID from JWT (automatically injected or extracted)
     * @return added language
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add a new language", description = "Add a new language to the list of supported languages.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Language added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid language data"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID")
    })
    public ResponseEntity<Language> addLanguage(
            @RequestBody
            @Parameter(description = "Language to add") Language language,
            @RequestHeader("X-Tenant-ID") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        return ResponseEntity.ok(languageService.saveLanguage(language));
    }

    /**
     * Delete a language by ID.
     *
     * Accessible only to users with the ADMIN role.
     * Validates tenant from JWT.
     *
     * @param id ID of the language to delete
     * @param jwtTenantId tenant ID from JWT (automatically injected or extracted)
     * @return no content response
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a language", description = "Delete an existing language by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Language deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Language not found"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID")
    })
    public ResponseEntity<Void> deleteLanguage(
            @PathVariable
            @Parameter(description = "ID of the language to delete") Long id,
            @RequestHeader("X-Tenant-ID") String jwtTenantId) {
        TenantUtil.validateTenant(jwtTenantId);
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}