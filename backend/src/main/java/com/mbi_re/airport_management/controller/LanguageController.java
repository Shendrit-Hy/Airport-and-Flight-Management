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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing supported languages in the system.
 *
 * <p>Supports operations to retrieve all languages, add a new language,
 * and delete an existing language.</p>
 *
 * <p>Tenant validation is enforced via "X-Tenant-ID" either from JWT or request headers.</p>
 */
@RestController
@RequestMapping("/api/languages")
@CrossOrigin
@Tag(name = "Languages", description = "Endpoints for managing supported languages")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    /**
     * Retrieves all available languages.
     *
     * <p>This endpoint is publicly accessible, including unauthenticated users,
     * but tenant context must be validated via the "X-Tenant-ID" header if no tenant
     * is already set.</p>
     *
     * @param request HTTP servlet request used to extract tenant header if needed.
     * @return List of all supported Language objects.
     * @throws IllegalArgumentException if the "X-Tenant-ID" header is missing when required.
     */
    @GetMapping
    @Operation(
            summary = "Get all languages",
            description = "Retrieve a list of all supported languages. Accessible to all users."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of languages returned successfully"),
            @ApiResponse(responseCode = "400", description = "Missing tenant header"),
            @ApiResponse(responseCode = "403", description = "Tenant validation failed")
    })
    public List<Language> getLanguages(
            @Parameter(description = "HTTP request for extracting X-Tenant-ID header if unauthenticated")
            HttpServletRequest request) {

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
     * Adds a new language to the system.
     *
     * <p>Accessible only to users with the ADMIN role. Tenant validation is performed
     * using the tenant ID from the JWT provided in the "X-Tenant-ID" header.</p>
     *
     * @param language The language object to add.
     * @param jwtTenantId Tenant ID extracted from JWT via the request header.
     * @return The saved Language object.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Add a new language",
            description = "Add a new language to the list of supported languages. Requires ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Language added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid language data"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or access denied")
    })
    public ResponseEntity<Language> addLanguage(
            @RequestBody
            @Parameter(description = "Language object to add") Language language,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID extracted from JWT token") String jwtTenantId) {

        TenantUtil.validateTenant(jwtTenantId);
        Language savedLanguage = languageService.saveLanguage(language);
        return ResponseEntity.ok(savedLanguage);
    }

    /**
     * Deletes a language by its ID.
     *
     * <p>Accessible only to users with the ADMIN role. Tenant validation is performed
     * using the tenant ID from the JWT provided in the "X-Tenant-ID" header.</p>
     *
     * @param id ID of the language to delete.
     * @param jwtTenantId Tenant ID extracted from JWT via the request header.
     * @return ResponseEntity with HTTP 204 No Content on successful deletion.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Delete a language",
            description = "Delete an existing language by its ID. Requires ADMIN role."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Language deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Language not found"),
            @ApiResponse(responseCode = "403", description = "Invalid tenant ID or access denied")
    })
    public ResponseEntity<Void> deleteLanguage(
            @PathVariable
            @Parameter(description = "ID of the language to delete") Long id,
            @RequestHeader("X-Tenant-ID")
            @Parameter(description = "Tenant ID extracted from JWT token") String jwtTenantId) {

        TenantUtil.validateTenant(jwtTenantId);
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}
