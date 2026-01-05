package com.example.demo.exception.auth.signup;

public class WeakPasswordException extends IllegalArgumentException {
    public WeakPasswordException() { super("비밀번호 강도 약함"); }
}
