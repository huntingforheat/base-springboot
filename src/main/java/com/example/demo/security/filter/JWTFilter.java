package com.example.demo.security.filter;

import com.example.demo.security.UsersDetailsService;
import com.example.demo.security.exception.AccessTokenException;
import com.example.demo.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UsersDetailsService usersDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String validationPath = "/api";

        // /api로 시작하지 않는 경우 리턴
        if (!path.startsWith(validationPath)) {
            filterChain.doFilter(request, response);

            return;
        }

        try {
            Map<String, Object> validationToken = validateAccessToken(request);

            // payload 값 들어옴 userId, 발급시간, 만료시간
            validationToken.forEach((key, value) -> log.info("key: {}, value: {}", key, value));

            if (validationToken.containsKey("userId")) {

                UserDetails userDetails = usersDetailsService.loadUserByUsername((String) validationToken.get("userId"));

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 다음 필터로 넘기기
                filterChain.doFilter(request, response);
            } else {

                // 인증 값으면 리턴
                return;
            }

        } catch (AccessTokenException accessTokenException) {
            accessTokenException.sendResponseError(response);
            log.error("accessToken error" + accessTokenException);
        }
    }

    // 클라이언트에서 보낸 Access Token 검증
    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {

        String headerStr = request.getHeader("Authorization");

        // 8미만으로 짜르는 이유는 headerStr이 "Bearer <access_token>" 이렇게 이루어져 있기 때문
        if(headerStr == null || headerStr.length() < 8) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.UNACCEPT);
        }

        String tokenType = headerStr.substring(0, 6);
        String tokenStr = headerStr.substring(7);

        // 타입이 Bearer인지 확인하고 올바르지 않으면 BADTYPE 예외를 던짐
        if(!tokenType.equalsIgnoreCase("Bearer")) {
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADTYPE);
        }

        try {
            Map<String, Object> validationToken = jwtUtil.validateToken(tokenStr);

            return validationToken;
        } catch (MalformedJwtException malformedJwtException) {
            log.info("MalformedJwtException-----------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.MALFORM);
        } catch (SignatureException signatureException) {
            log.info("SignatureException---------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.BADSIGN);
        } catch (ExpiredJwtException expiredJwtException) {
            log.info("ExpiredJwtException--------------------------");
            throw new AccessTokenException(AccessTokenException.TOKEN_ERROR.EXPIRED);
        }
    }
}
