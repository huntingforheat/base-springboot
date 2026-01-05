package com.example.demo.exception.auth.signup;

public class DuplicateUserIdException extends IllegalArgumentException {
    public DuplicateUserIdException() { super("아이디 중복"); }
}
