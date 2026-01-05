package com.example.demo.exception.auth.modifyuser;

public class ValidationException extends IllegalArgumentException {
    public ValidationException() { super("입력 검증 실패"); }
}
