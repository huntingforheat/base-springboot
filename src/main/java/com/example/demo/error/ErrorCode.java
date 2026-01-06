package com.example.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    VALIDATION_FAIL(400, "A001", "입력 검증 실패"),
    DUPLICATE_USERID(409, "A002","아이디 중복"),
    DUPLICATE_EMAIL(409, "A003","이메일 중복"),
    DUPLICATE_NICKNAME(409, "A004", "닉네임 중복"),
    WEAK_PASSWORD(400, "A005", "비밀번호 강도 약함"),
    BAD_CREDENTIALS (401, "A006", "인증 실패"),
    USER_NOT_FOUND(404, "A007", "회원 없음"),
    ACCOUNT_LOCKED(403, "A008", "계정 잠김"),
    AUTH_REQUIRED(401, "A009", "로그인 안함"),
    UNAUTHORIZED_USER(403, "A010", "본인 아님"),

    INTERNAL_SERVER_ERROR(500, "X001", "서버 오류");

    private int status;
    private final String code;
    private final String message;
}
