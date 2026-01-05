package com.example.demo.exception.auth.signin;

public class AccountLockedException extends IllegalArgumentException {
    public AccountLockedException() { super("계정 잠김"); }
}
