package com.example.demo.service.auth;

import com.example.demo.domain.auth.Users;
import com.example.demo.dto.auth.AuthSignupDTO;
import com.example.demo.exception.auth.signup.DuplicateEmailException;
import com.example.demo.exception.auth.signup.DuplicateUserIdException;
import com.example.demo.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Users signup(AuthSignupDTO authSignupDTO) {

        boolean existUserId = usersRepository.existsByUserId(authSignupDTO.getUserId());
        boolean existEmail = usersRepository.existsByEmail(authSignupDTO.getEmail());

        // 아이디 중복인 경우
        if (existUserId) {
            throw new DuplicateUserIdException();
        }

        // 이메일 중복인 경우
        if (existEmail) {
            throw new DuplicateEmailException();
        }

        Users user =Users.builder()
                .userId(authSignupDTO.getUserId())
                .password(passwordEncoder.encode(authSignupDTO.getPassword()))
                .email(authSignupDTO.getEmail())
                .name(authSignupDTO.getName())
                .phoneNumber(authSignupDTO.getPhoneNumber())
                .regDtm(LocalDateTime.now())
                .regId("api")
                .build();

        usersRepository.save(user);

        return user;
    }
}
