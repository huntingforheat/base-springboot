package com.example.demo.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        AntPathMatcher matcher = new AntPathMatcher();
        String requestPath = request.getRequestURI();
        // TODO: 테스트로 임시변경
        boolean isMatch = matcher.match("/test", requestPath);

        // /refreshToken으로 요청 들어 왔을때
        if (isMatch) {

            Map<String, Object> tokens = parseRequestJSON(request);

        } else {
            log.info("skip refreshToken filter");
            filterChain.doFilter(request, response);
            return;
        }
    }

    private Map<String, Object> parseRequestJSON(HttpServletRequest request) {

        Map<String, Object> tokens = new HashMap<>();

        try {

            String contentType = request.getContentType();

            if (contentType != null && contentType.contains("application/json")) {

                StringBuilder sb = new StringBuilder();
                BufferedReader br = request.getReader();
            }
        } catch (Exception e) {

        }

        return null;
    }
}
