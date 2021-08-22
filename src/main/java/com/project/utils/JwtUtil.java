package com.project.utils;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    public static final Logger LOG = LoggerFactory.getLogger(JwtUtil.class);

    private final long expires = System.currentTimeMillis() + 1000L * 60 * 60;
    private final String secret = "shoeshop";

    public String generateJwtToken(UserDetails userDetails) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expires))
                .signWith(SignatureAlgorithm.HS256, secret);
        String jwt = jwtBuilder.compact();
        return jwt;
    }

    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            JwtParser jwtParser = Jwts.parser().setSigningKey(secret);
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expires);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: {}",e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}",e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expiried: {}",e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}",e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}",e.getMessage());
        }
        return false;
    }
}
