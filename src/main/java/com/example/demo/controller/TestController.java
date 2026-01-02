package com.example.demo.controller;

import com.example.demo.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class TestController {

    private final JWTUtil jwtUtil;

    @PostMapping("/test")
    public String test() {
      log.info("test 컨트롤럴 진입");

        Map<String, Object> claims = Map.of(
          "userId", "1234"
        );

      String si = jwtUtil.createJwt(claims, 1);

      return "OK";
    }
}
