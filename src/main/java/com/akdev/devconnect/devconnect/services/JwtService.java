package com.akdev.devconnect.devconnect.services;

import com.akdev.devconnect.devconnect.model.UsersModel;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    // Fetch secret key from application.properties or environment variable
    private String secret = "";

    // Expiration time in milliseconds (1 day)
    private int expirationTime = 86400000;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    // Method to generate a JWT token
    public String generateToken(String username, String email, UsersModel user) {
        return Jwts.builder()
                .setSubject(email)
                .claim("username", username)
                .claim("userId", user.getId())
                .claim("userName", user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Method to validate the JWT token
    public Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("Token expired", e);
        } catch (UnsupportedJwtException e) {
            throw new JwtTokenUnsupportedException("Unsupported token", e);
        } catch (MalformedJwtException e) {
            throw new JwtTokenMalformedException("Invalid token", e);
        } catch (SignatureException e) {
            throw new JwtTokenInvalidSignatureException("Invalid signature", e);
        } catch (IllegalArgumentException e) {
            throw new JwtTokenEmptyException("Token is empty or null", e);
        }
    }

    // Custom exceptions for specific error cases
    public static class JwtTokenExpiredException extends RuntimeException {
        public JwtTokenExpiredException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class JwtTokenUnsupportedException extends RuntimeException {
        public JwtTokenUnsupportedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class JwtTokenMalformedException extends RuntimeException {
        public JwtTokenMalformedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class JwtTokenInvalidSignatureException extends RuntimeException {
        public JwtTokenInvalidSignatureException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class JwtTokenEmptyException extends RuntimeException {
        public JwtTokenEmptyException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
