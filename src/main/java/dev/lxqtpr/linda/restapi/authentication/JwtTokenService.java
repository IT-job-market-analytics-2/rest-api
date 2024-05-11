package dev.lxqtpr.linda.restapi.authentication;

import dev.lxqtpr.linda.restapi.core.exceptions.JwtException;
import dev.lxqtpr.linda.restapi.core.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenService {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final JwtProperties jwtProperties;

    public JwtTokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getAccessSecret()));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getRefreshSecret()));
    }

    public String generateAccessToken(String username) {
        var now = LocalDateTime.now();
        var accessExpirationInstant =
                now.plusMinutes(jwtProperties.getAccessExpirationInMinutes())
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
        var accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        var now = LocalDateTime.now();
        var refreshExpirationInstant =
                now
                        .plusMinutes(jwtProperties.getAccessExpirationInMinutes())
                        .atZone(ZoneId.systemDefault())
                        .toInstant();
        var refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(String token,SecretKey secret) {
        try {
            Jwts
                    .parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }
        catch (Exception e){
            throw new JwtException(e.getMessage());
        }
    }

    public String getUsernameFromAccessClaims(String token) {
        return getClaims(token, jwtAccessSecret).getSubject();
    }

    public String getUsernameFromRefreshClaims(String token) {
        return getClaims(token, jwtRefreshSecret).getSubject();
    }

    private Claims getClaims(String token,SecretKey secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}