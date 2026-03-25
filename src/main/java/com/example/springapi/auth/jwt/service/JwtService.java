package com.example.springapi.auth.jwt.service;

import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JwtService
{
    private final String SECRET_KEY = "eWdHNmhSM3Y4TmYyS3E5THBRdzFYejRDZTVWbTYiVHg4VQ==";
    /* private final UserService userService;
    public JwtService(UserService userService)
    {
        this.userService = userService;

    }*/
    public String generationToken(User user)
    {
        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId",user.getId())
                .claim("username",user.getUsername())
                .claim("role",user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        log.info("JWT token generation for userId={}", user.getId());
        return token;
    }

    public String extractEmail(String token)
    {
        return extractAllClaims(token).getSubject();
    }
    public Long extractUserId(String token)
    {
        return extractAllClaims(token).get("userId", Long.class);
    }
    public String extractRole(String token)
    {
        return extractAllClaims(token).get("role", String.class);
    }
    public String extractUsername(String token)
    {
        return extractAllClaims(token).get("username", String.class);
    }

    public boolean isTokenValid(String token, User user)
    {
        String email = extractEmail(token);
        return email.equals(user.getEmail())&&!isTokenExpired(token);
    }
    private boolean isTokenExpired(String token)
    {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public Claims extractAllClaims(String token)
    {
        try
        {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        }
        catch(Exception e)
        {
            log.warn("Failed to parse token:{}",e.getMessage());
            throw e;
        }


    }
    private Key getSignKey()
    {

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
