package com.example.demo.handler;

import com.example.demo.error.ErrorCode;
import com.example.demo.error.ErrorResponse;
import com.example.demo.exception.auth.AuthException;
import com.example.demo.exception.auth.modifyuser.AuthenticationRequiredException;
import com.example.demo.exception.auth.modifyuser.UnauthorizedUserException;
import com.example.demo.exception.auth.signin.AccountLockedException;
import com.example.demo.exception.auth.signin.BadCredentialsException;
import com.example.demo.exception.auth.signin.UserNotFoundException;
import com.example.demo.exception.auth.signup.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {

    // 회원가입 - 입력 검증 실패
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleSignupValidationFailException(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.VALIDATION_FAIL);
        return ResponseEntity.status(ErrorCode.VALIDATION_FAIL.getStatus()).body(response);
    }

    // 회원가입 - 아이디 중복
    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponse> handleSignupDuplicateUserId(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_USERID);
        return ResponseEntity.status(ErrorCode.DUPLICATE_USERID.getStatus()).body(response);
    }

    // 회원가입 - 이메일 중복
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleSignupDuplicateEmail(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_EMAIL);
        return ResponseEntity.status(ErrorCode.DUPLICATE_EMAIL.getStatus()).body(response);
    }

    // 회원가입 - 닉네임 중복
    @ExceptionHandler(DuplicateNicknameException.class)
    public ResponseEntity<ErrorResponse> handleSignupDuplicateNickname(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_NICKNAME);
        return ResponseEntity.status(ErrorCode.DUPLICATE_NICKNAME.getStatus()).body(response);
    }

    // 회원가입 - 비밀번호 강도 약함
    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<ErrorResponse> handleSignupWeakPassword(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.WEAK_PASSWORD);
        return ResponseEntity.status(ErrorCode.WEAK_PASSWORD.getStatus()).body(response);
    }

    // 로그인  - 인증 실패
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleSigninBadCredentials(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.BAD_CREDENTIALS);
        return ResponseEntity.status(ErrorCode.BAD_CREDENTIALS.getStatus()).body(response);
    }

    // 로그인  - 회원 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSigninUserNotFound(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);
        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus()).body(response);
    }

    // 로그인  - 계정 잠김
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponse> handleSigninAccountLocked(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.ACCOUNT_LOCKED);
        return ResponseEntity.status(ErrorCode.ACCOUNT_LOCKED.getStatus()).body(response);
    }

    // 회원정보 수정 - 로그인 안함
    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<ErrorResponse> handleModifyUserAuthRequired(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.AUTH_REQUIRED);
        return ResponseEntity.status(ErrorCode.AUTH_REQUIRED.getStatus()).body(response);
    }

    // 회원정보 수정 - 본인 아님
    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorResponse> handleModifyUserUnauthorizedUser(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.UNAUTHORIZED_USER);
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED_USER.getStatus()).body(response);
    }

    // 회원정보 수정 - 회원 없음
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleModifyUserNotFound(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.USER_NOT_FOUND);
        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus()).body(response);
    }

    // 회원정보 수정 - 닉네임 중복
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.DuplicateNicknameException.class)
    public ResponseEntity<ErrorResponse> handleModifyUserDuplicateNickname(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.DUPLICATE_NICKNAME);
        return ResponseEntity.status(ErrorCode.DUPLICATE_NICKNAME.getStatus()).body(response);
    }

    // 회원정보 수정 - 입력 검증 실패
    @ExceptionHandler(com.example.demo.exception.auth.modifyuser.ValidationException.class)
    public ResponseEntity<ErrorResponse> handleModifyUserValidationFail(AuthException e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.VALIDATION_FAIL);
        return ResponseEntity.status(ErrorCode.VALIDATION_FAIL.getStatus()).body(response);
    }

    // 그 밖에 발생하는 모든 예외처리가 이곳으로 모인다.
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(response);
    }
}
