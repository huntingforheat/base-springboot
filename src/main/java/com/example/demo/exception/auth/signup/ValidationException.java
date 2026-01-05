package com.example.demo.exception.auth.signup;

public class ValidationException extends IllegalArgumentException {
    public ValidationException() { super("회원가입 입력 검증 실패"); }
}
