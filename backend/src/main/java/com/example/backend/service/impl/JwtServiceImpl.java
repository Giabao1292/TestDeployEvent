package com.example.backend.service.impl;

import com.example.backend.service.JwtService;
import com.example.backend.util.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.example.backend.util.TokenType.ACCESS_TOKEN;
import static com.example.backend.util.TokenType.REFRESH_TOKEN;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {


    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.refreshKey}")
    private String refreshKey;

    @Value("${jwt.limit.day}")
    private Integer limitDay;

    @Value("${jwt.limit.hour}")
    private Integer limitHour;

    @Override
    public String extractUsername(String token, TokenType type) {
        return extractClaimsFromToken(token, Claims::getSubject, type);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, limitHour, ACCESS_TOKEN);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails, limitDay * 24, REFRESH_TOKEN);
    }

    public String generateToken(Map<String, Object> claims, UserDetails userDetails, Integer limitTime, TokenType type) {
        return Jwts.builder()
                .setClaims(claims)
                .claim("roles", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * limitTime))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey(type), SignatureAlgorithm.HS512)
                .compact();
    }

    public Key getKey(TokenType type) {
        byte[] encodedKey = null;
        if (ACCESS_TOKEN.equals(type)) {
            encodedKey = Decoders.BASE64.decode(secretKey);
        } else {
            encodedKey = Decoders.BASE64.decode(refreshKey);
        }
        return Keys.hmacShaKeyFor(encodedKey);
    }

    private Claims getClaimsFromToken(String token, TokenType type) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey(type))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            return null;
        }
    }

    private <T> T extractClaimsFromToken(String token, Function<Claims, T> claimsResolver, TokenType type) {
        final Claims claims = getClaimsFromToken(token, type);
        if (claims != null) {
            return claimsResolver.apply(claims);
        }
        return null;
    }
}