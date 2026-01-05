package com.example.demo.exception.auth.modifyuser;

public class UserNotFoundException extends IllegalArgumentException {
    public UserNotFoundException() { super("회원 없음"); }
}
