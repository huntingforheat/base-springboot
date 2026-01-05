package com.example.demo.dto.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AuthSignupDTO {

    @NotBlank(message = "아이디 값은 필수 입니다.")
    @Size(min = 4, max = 20)
    private String userId;

    @NotBlank(message = "비밀번호 값은 필수 입니다.")
    @Max(60)
    // SpringSecurity의 passwordEncoder를 사용 했을떄 비밀번호 자릿수는 60자리 고정
    private String password;

    @NotBlank(message = "이름 값은 필수 입니다.")
    private String name;

    @NotBlank(message = "이메일 값은 필수 입니다.")
    @Email
    private String email;

    private String phoneNumber;

    @NotNull(message = "가입시간 값은 필수 입니다.")
    private LocalDateTime regDtm;

    private String regId;
}
