package com.example.demo.controller;

import com.example.demo.dto.auth.AuthSignupDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
@EnableWebSecurity
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody @Valid AuthSignupDTO signupDTO) {

    }
}
