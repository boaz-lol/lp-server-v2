package com.lp.v2.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ResponseMessage {
    // 공통 응답
    SUCCESS("성공", HttpStatus.OK),
    BAD_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND("리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CONFLICT("충돌이 발생했습니다.", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 계정 관련 응답
    ACCOUNT_CREATED_SUCCESS("계정 생성이 완료되었습니다.", HttpStatus.CREATED),
    ACCOUNT_DUPLICATE_ERROR("이미 존재하는 계정입니다.", HttpStatus.CONFLICT),
    ACCOUNT_NOT_FOUND("계정을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    ACCOUNT_DELETE_SUCCESS("계정 삭제가 완료되었습니다.", HttpStatus.OK),
    ACCOUNT_UPDATE_SUCCESS("계정 정보가 수정되었습니다.", HttpStatus.OK),

    // 인증 관련 응답
    LOGIN_SUCCESS("로그인이 완료되었습니다.", HttpStatus.OK),
    LOGIN_FAILED("로그인에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    LOGOUT_SUCCESS("로그아웃이 완료되었습니다.", HttpStatus.OK),
    TOKEN_EXPIRED("토큰이 만료되었습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID("유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_REFRESH_SUCCESS("토큰 갱신이 완료되었습니다.", HttpStatus.OK),
    INVALID_CREDENTIALS("잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED),
    PASSWORD_VERIFICATION_SUCCESS("비밀번호 인증이 완료되었습니다.", HttpStatus.OK);

    private final String message;
    private final HttpStatus httpStatus;

    public int getStatusCode() {
        return httpStatus.value();
    }
}