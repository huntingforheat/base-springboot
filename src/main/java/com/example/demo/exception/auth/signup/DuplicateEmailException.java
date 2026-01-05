package com.example.demo.exception.auth.signup;

public class DuplicateEmailException extends IllegalArgumentException {
    public DuplicateEmailException() { super("이메일 중복"); }
}
