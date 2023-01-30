package com.sparta.team_1_hyogeunchild.enums;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorMessageCode {
    //400에러
    DUPLICATED_USER(BAD_REQUEST, "중복된 닉네임이 존재합니다."),
    TOKEN_FALSE(UNAUTHORIZED, "사용자 권한이 없습니다."),
    PASSWORD_FALSE(FORBIDDEN, "Password 가 일치하지 않습니다."),
    NOT_FOUND_POST(NOT_FOUND, "존재하지 않는 게시글 입니다."),
    NOT_FOUND_TOKEN(NOT_FOUND, "존재하지 않는 댓글 입니다."),
    NOT_FOUND_USER(NOT_FOUND, "존재하지 않는 사용자 입니다."),
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorMessageCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.errorMessage = message;
    }
}
