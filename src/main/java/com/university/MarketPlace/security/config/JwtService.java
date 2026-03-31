package com.university.MarketPlace.security.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static SecretKey key;

    public JwtService(@Value("${security.jwt.secret}") String secret) {
        try {
            this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT secret key: " + e.getMessage(), e);
        }
    }

    public static String generateToken(String email, String roles) {
        return Jwts.builder()
                .subject(email)
                .claim("roles",roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        Claims claims = extractClaims(token);
        return "refresh".equals(claims.get("type"));
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

//    public boolean validateRefreshToken(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(key)
//                    .build()
//                    .parseSignedClaims(token);
//
//            return true; // valid
//        } catch (ExpiredJwtException e) {
//            System.out.println("Token expired");
//        } catch (UnsupportedJwtException e) {
//            System.out.println("Unsupported token");
//        } catch (MalformedJwtException e) {
//            System.out.println("Malformed token");
//        } catch (SignatureException e) {
//            System.out.println("Invalid signature");
//        } catch (IllegalArgumentException e) {
//            System.out.println("Token is empty");
//        }
//        return false;
//    }

//    public String generateRefreshToken(String name) {
//        return Jwts.builder()
//                .subject(name)
//                .claim("type", "refresh")
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + 604800000))
//                .signWith(key)
//                .compact();
//    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
        return claims.getSubject();
    }
}