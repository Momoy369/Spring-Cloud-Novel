package com.java2nb.novel.core.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java2nb.novel.core.bean.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 11797
 */
@Component
@Slf4j
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Menurut token yang bertanggung jawab untuk menghasilkan JWT
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Dapatkan beban di JWT dari token
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.info("Verifikasi format JWT gagal:{}",token);
        }
        return claims;
    }

    /**
     * Waktu kedaluwarsa token yang dibuat
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Dapatkan informasi pengguna dari token
     */
    public UserDetails getUserDetailsFromToken(String token) {
        if(isTokenExpired(token)){
            return null;
        }
        UserDetails userDetail;
        try {
            Claims claims = getClaimsFromToken(token);
             userDetail = new ObjectMapper().readValue(claims.getSubject(),UserDetails.class);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            userDetail = null;
        }
        return userDetail;
    }


    /**
     * Tentukan apakah token telah kedaluwarsa
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        if(expiredDate == null){
            return true;
        }else {
            return expiredDate.before(new Date());
        }
    }

    /**
     * Dapatkan waktu kedaluwarsa dari token
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * Hasilkan token berdasarkan informasi pengguna
     */
    @SneakyThrows
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USERNAME, new ObjectMapper().writeValueAsString(userDetails));
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * Tentukan apakah token dapat disegarkan
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * Segarkan token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


}
