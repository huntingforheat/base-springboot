package com.example.demo.exception.auth.signup;

public class DuplicateNicknameException extends IllegalArgumentException {
    public DuplicateNicknameException() { super("닉네임 중복"); }
}
