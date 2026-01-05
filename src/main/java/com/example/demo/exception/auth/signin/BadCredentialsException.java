package com.example.demo.exception.auth.signin;

public class BadCredentialsException extends IllegalArgumentException{
    public BadCredentialsException() { super("인증 실패"); }
}
