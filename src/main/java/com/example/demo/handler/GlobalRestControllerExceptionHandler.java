package com.example.demo.handler;

import com.example.demo.exception.auth.AuthException;
import com.example.demo.exception.auth.modifyuser.AuthenticationRequiredException;
import com.example.demo.exception.auth.modifyuser.UnauthorizedUserException;
import com.example.demo.exception.auth.signin.AccountLockedException;
import com.example.demo.exception.auth.signin.BadCredentialsException;
import com.example.demo.exception.auth.signin.UserNotFoundException;
import com.example.demo.exception.auth.signup.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {

    // 회원가입 - 입력 검증 실패
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleSignupValidationFailException(AuthException e) {

        ResponseStatusException(404, )
        return ResponseEntity.status(AuthException.SIGNUP_ERROR.VALIDATION_FAIL.getStatus()).body(AuthException.SIGNUP_ERROR.VALIDATION_FAIL.response());
    }

    // 회원가입 - 아이디 중복
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<?> handleSignupDuplicateUserId(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNUP_ERROR.DUPLICATE_USERID.getStatus()).body(AuthException.SIGNUP_ERROR.DUPLICATE_USERID.response());
    }

    // 회원가입 - 이메일 중복
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleSignupDuplicateEmail(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNUP_ERROR.DUPLICATE_EMAIL.getStatus()).body(AuthException.SIGNUP_ERROR.DUPLICATE_EMAIL.response());
    }

    // 회원가입 - 닉네임 중복
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<?> handleSignupDuplicateNickname(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNUP_ERROR.DUPLICATE_NICKNAME.getStatus()).body(AuthException.SIGNUP_ERROR.DUPLICATE_NICKNAME.response());
    }

    // 회원가입 - 비밀번호 강도 약함
    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<?> handleSignupWeakPassword(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNUP_ERROR.WEAK_PASSWORD.getStatus()).body(AuthException.SIGNUP_ERROR.WEAK_PASSWORD.response());
    }

    // 로그인  - 인증 실패
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleSigninBadCredentials(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNIN_ERROR.BAD_CREDENTIALS.getStatus()).body(AuthException.SIGNIN_ERROR.BAD_CREDENTIALS.response());
    }

    // 로그인  - 회원 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleSigninUserNotFound(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNIN_ERROR.USER_NOT_FOUND.getStatus()).body(AuthException.SIGNIN_ERROR.USER_NOT_FOUND.response());
    }

    // 로그인  - 계정 잠김
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<?> handleSigninAccountLocked(AuthException e) {
        return ResponseEntity.status(AuthException.SIGNIN_ERROR.ACCOUNT_LOCKED.getStatus()).body(AuthException.SIGNIN_ERROR.ACCOUNT_LOCKED.response());
    }

    // 회원정보 수정 - 로그인 안함
    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<?> handleModifyUserAuthRequired(AuthException e) {
        return ResponseEntity.status(AuthException.MODIFY_USER_ERROR.AUTH_REQUIRED.getStatus()).body(AuthException.MODIFY_USER_ERROR.AUTH_REQUIRED.response());
    }

    // 회원정보 수정 - 본인 아님
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<?> handleModifyUserUnauthorizedUser(AuthException e) {
        return ResponseEntity.status(AuthException.MODIFY_USER_ERROR.UNAUTHORIZED_USER.getStatus()).body(AuthException.MODIFY_USER_ERROR.UNAUTHORIZED_USER.response());
    }

    // 회원정보 수정 - 회원 없음
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.UserNotFoundException.class)
    public ResponseEntity<?> handleModifyUserNotFound(AuthException e) {
        return ResponseEntity.status(AuthException.MODIFY_USER_ERROR.USER_NOT_FOUND.getStatus()).body(AuthException.MODIFY_USER_ERROR.USER_NOT_FOUND.response());
    }

    // 회원정보 수정 - 닉네임 중복
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.DuplicateNicknameException.class)
    public ResponseEntity<?> handleModifyUserDuplicateNickname(AuthException e) {
        return ResponseEntity.status(AuthException.MODIFY_USER_ERROR.DUPLICATE_NICKNAME.getStatus()).body(AuthException.MODIFY_USER_ERROR.DUPLICATE_NICKNAME.response());
    }

    // 회원정보 수정 - 입력 검증 실패
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.ValidationException.class)
    public ResponseEntity<?> handleModifyUserValidationFail(AuthException e) {
        return ResponseEntity.status(AuthException.MODIFY_USER_ERROR.VALIDATION_FAIL.getStatus()).body(AuthException.MODIFY_USER_ERROR.VALIDATION_FAIL.response());
    }
}
