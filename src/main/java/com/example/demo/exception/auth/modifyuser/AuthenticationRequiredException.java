package com.example.demo.exception.auth.modifyuser;

public class AuthenticationRequiredException extends IllegalArgumentException {
    public AuthenticationRequiredException() { super("로그인 안함"); }
}
