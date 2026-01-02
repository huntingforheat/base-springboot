package com.example.demo.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JWTUtil {

    private final SecretKey secretKey;

    /**
     * 생성자에서 application.properties에 저장된 SecretKey 값을 가져와 설정
     */
    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        log.info("secretKey: " + secretKey);
    }

    // 토근 생성
    public String createJwt(Map<String, Object> valueMap, int days) {

        // 헤더 부분
        Map<String, Object> headers = Map.of(
                "typ", "JWT",
                "alg", "HS256"
        );

        // payload 부분
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);


        // 테스트 할때는 plusDays(days) 변경 해야 함
        String jwtStr = Jwts.builder()
                .header()
                    .empty()
                    .add(headers)
                .and()
                .claims()
                    .empty()
                    .add(payloads)
                .and()
                .issuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .expiration(Date.from(ZonedDateTime.now().plusDays(days).toInstant()))
                .signWith(secretKey)
                .compact();

        return jwtStr;
    }

    /**
     * JWT에서 username 추출
     */
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("username", String.class);
    }

    /**
     * JWT에서 role(권한) 추출
     */
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(token)
                .getPayload()
                .get("role", String.class);
    }

    public Map<String, Object> validateToken(String token) throws JwtException {

        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("토큰 유효시간이 만료되었습니다: " + e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 형식입니다: " + e.getMessage());
            throw e;
        } catch (MalformedJwtException e) {
            log.error("토큰의 형식이나 구조가 올바르지 않습니다: " + e.getMessage());
            throw e;
        } catch (SignatureException e) {
            log.error("토큰의 서명이 올바르지 않습니다: " + e.getMessage());
            throw e;
        } catch (PrematureJwtException e) {
            log.error("토큰이 아직 유효하지 않습니다: " + e.getMessage());
            throw e;
        } catch (ClaimJwtException e) {
            log.error("클레임 값이 잘못됐습니다: " + e.getMessage());
            throw e;
        } catch (JwtException e) {
            log.error("토큰이 유효하지 않습니다: " + e.getMessage());
            throw e;
        }
    }


}
