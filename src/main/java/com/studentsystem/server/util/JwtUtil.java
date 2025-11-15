package com.studentsystem.server.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.studentsystem.server.model.User; // Make sure to import your User model

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component // This makes it a Spring Bean, so we can inject it
public class JwtUtil {

    // This pulls the secret key from your application.properties
    @Value("${jwt.secret-key}")
    private String secretKeyString;

    private SecretKey getSigningKey() {
        // We must convert the string key into a secure key object
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    /**
     * Generates a new JWT for a given user.
     */
    public String generateToken(User user) {
        Instant now = Instant.now();

        // The token will be valid for 1 day
        Instant expiryTime = now.plus(1, ChronoUnit.DAYS);

        return Jwts.builder()
                // "subject" is the user's ID
                .subject(user.getId().toString())
                // "issuer" is our app
                .issuer("student-system-server")
                // "issuedAt" is the time it was created
                .issuedAt(Date.from(now))
                // "expiration" is when it stops working
                .expiration(Date.from(expiryTime))
                // --- Custom "Claims" ---
                // We can add extra, non-sensitive data here
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                // Sign it with our secret key
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Validates a token and returns its "claims" (the data).
     */
    public Claims parseToken(String token) {
        // This will check the signature and expiration date.
        // If it fails, it throws an exception.
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Gets the User ID from a valid token.
     */
    public UUID getUserIdFromToken(String token) {
        String subject = parseToken(token).getSubject();
        return UUID.fromString(subject);
    }
    
    /**
     * Checks if a token is valid (i.e., not expired and signed by us).
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            // Token is invalid (expired, wrong signature, etc.)
            return false;
        }
    }
}