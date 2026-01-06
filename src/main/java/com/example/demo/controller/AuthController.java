package com.example.demo.controller;

import com.example.demo.domain.auth.Users;
import com.example.demo.dto.auth.AuthSignupDTO;
import com.example.demo.global.ResultCode;
import com.example.demo.global.ResultResponse;
import com.example.demo.service.auth.AuthService;
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

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResultResponse> signup(@RequestBody @Valid AuthSignupDTO signupDTO) {

        Users user = authService.signup(signupDTO);

        ResultResponse response = ResultResponse.of(ResultCode.REGISTER_SUCCESS, user.getUserId() + " 회원 가입완료");

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
