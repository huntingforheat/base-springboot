package com.example.demo.service.auth;

import com.example.demo.domain.auth.Users;
import com.example.demo.dto.auth.AuthSignupDTO;

public interface AuthService {

    Users signup(AuthSignupDTO authSignupDTO);
}
