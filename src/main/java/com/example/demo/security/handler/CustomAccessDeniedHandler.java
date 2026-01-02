package com.example.demo.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 로깅
        log.warn("Access Denied: {} - {}",
                request.getRequestURI(),
                accessDeniedException.getMessage());

        // AJAX 요청 처리
        if (isAjaxRequest(request)) {
            handleAjaxRequest(response, accessDeniedException);
        } else {
            handleWebRequest(request, response);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) ||
                request.getRequestURI().startsWith("/api/");
    }

    private void handleAjaxRequest(HttpServletResponse response,
                                   AccessDeniedException exception)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("error", "Access Denied");
        errorDetails.put("message", "접근 권한이 없습니다");
        errorDetails.put("status", 403);

        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }

    private void handleWebRequest(HttpServletRequest request,
                                  HttpServletResponse response)
            throws IOException {
        // 요청 URL을 세션에 저장 (돌아가기 기능용)
        request.getSession().setAttribute("PREVIOUS_URL", request.getRequestURI());

        response.sendRedirect("/error/403");
    }
}
