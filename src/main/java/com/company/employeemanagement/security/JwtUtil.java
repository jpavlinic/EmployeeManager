package com.company.employeemanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Secret key for signing tokens
    private final Key key;

    // Token expiration time
    private final long expiration;

    /**
     * Constructor: Initialize secret key and expiration time from application.properties.
     */
    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expiration = expiration;
    }

    /**
     * Generate a JWT token for a given username.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Create a JWT token with claims and subject.
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)                               // Add custom claims if needed
                .setSubject(subject)                             // User's identifier (usually username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token creation time
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Token expiration
                .signWith(key, SignatureAlgorithm.HS512)         // Sign with secret key (HMAC-SHA512)
                .compact();
    }

    /**
     * Validate the token's authenticity and expiration.
     */
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username) && !isTokenExpired(token);
    }

    /**
     * Extract the username (subject) from the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Check if the token has expired.
     */
    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    /**
     * Generalized method to extract any claim from the token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parse the token and extract all claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)  // Set the secret key for signature validation
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
