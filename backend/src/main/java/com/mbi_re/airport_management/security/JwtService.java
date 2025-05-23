package com.mbi_re.airport_management.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Service class responsible for generating, validating, and parsing JWT tokens.
 * It supports multi-tenant token generation and claims extraction.
 */
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private long jwtExpirationInMs;

    private Key key;

    /**
     * Initializes the HMAC signing key using the secret from configuration.
     * Ensures the secret is of sufficient length for HMAC-SHA256 encryption.
     */
    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.length() < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long.");
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Generates a JWT token with a username, role, and tenant ID.
     *
     * @param username the username for which the token is generated
     * @param role     the user's role to embed in the token
     * @param tenantId the tenant ID to embed in the token
     * @return a signed JWT token string
     */
    public String generateToken(String username, String role, String tenantId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("tenantId", tenantId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Generates a JWT token with a username and tenant ID only (role is omitted).
     *
     * @param username the username
     * @param tenantId the tenant ID
     * @return a signed JWT token string
     */
    public String generateToken(String username, String tenantId) {
        return generateToken(username, null, tenantId);
    }

    /**
     * Validates the given JWT token by checking its signature and expiration.
     *
     * @param token the JWT token to validate
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return the username (subject) from the token
     */
    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * Extracts the tenant ID from the JWT token.
     *
     * @param token the JWT token
     * @return the tenant ID from the token claims
     */
    public String getTenantId(String token) {
        return getClaims(token).get("tenantId", String.class);
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token the JWT token
     * @return the role from the token claims
     */
    public String getRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    /**
     * Parses the JWT token and returns its claims.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
