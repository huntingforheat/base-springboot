package com.example.demo.exception.auth;


import com.example.demo.dto.util.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

public class AuthException extends IllegalArgumentException {

    @Getter
    @AllArgsConstructor
    public enum SIGNUP_ERROR implements ErrorResponse {

        // 회원가입시 예외
        VALIDATION_FAIL(400, "입력 검증 실패"),
        DUPLICATE_USERID(409, "아이디 중복"),
        DUPLICATE_EMAIL(409, "이메일 중복"),
        DUPLICATE_NICKNAME(409, "닉네임 중복"),
        WEAK_PASSWORD(400, "비밀번호 강도 약함");

        private final int status;
        private final String description;

        @Override
        public Map<String, Object> response() {
            return Map.of(
                    "status",status,
                    "msg", description,
                    "time", new Date()
            );
        }
    }

    @Getter
    @AllArgsConstructor
    public enum SIGNIN_ERROR implements ErrorResponse {

        // 로그인시 예외
        BAD_CREDENTIALS (401, "인증 실패"),
        USER_NOT_FOUND(404, "회원 없음"),
        ACCOUNT_LOCKED(403, "계정 잠김");

        private final int status;
        private final String description;

        @Override
        public Map<String, Object> response() {
            return Map.of(
                    "status",status,
                    "msg", description,
                    "time", new Date()
                    );
        }
    }

    @Getter
    @AllArgsConstructor
    public enum MODIFY_USER_ERROR implements ErrorResponse {
        // 회원정보 수정 예외
        AUTH_REQUIRED(401, "로그인 안함"),
        UNAUTHORIZED_USER(403, "본인 아님"),
        USER_NOT_FOUND(404, "회원 없음"),
        DUPLICATE_NICKNAME(409, "닉네임 중복"),
        VALIDATION_FAIL(400, "입력 검증 실패")
        ;

        private final int status;
        private final String description;

        @Override
        public Map<String, Object> response() {
            return Map.of(
                    "status",status,
                    "msg", description,
                    "time", new Date()
            );
        }
    }
}
