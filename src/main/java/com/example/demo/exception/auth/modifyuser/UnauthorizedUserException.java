package com.example.demo.exception.auth.modifyuser;

public class UnauthorizedUserException extends IllegalArgumentException {
    public UnauthorizedUserException() { super("본인 아님"); }
}
