package com.taquitosncapas.helpinghands.utils;

import com.taquitosncapas.helpinghands.models.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenManager {

    private String jwtSecret = "jwt.secret";

    private static final long TOKEN_EXP_TIME_MILLIS = 60*60 * 1000;

    public String generateJwtToken(User user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXP_TIME_MILLIS))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Boolean validateJwtToken(String token, String givenUsername) {
        try {
            String email = getEmailFromToken(token);

            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();

            Boolean isTokenExpired = claims.getExpiration().before(new Date());

            return (email.equals(givenUsername) && !isTokenExpired);
        }catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {

        final Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();

    }

}

