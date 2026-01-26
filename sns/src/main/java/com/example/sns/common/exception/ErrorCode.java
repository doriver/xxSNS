package com.example.sns.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
    자주사용되는 ExpectedException들 정리및 관리
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저
    NEED_TO_LOGIN(HttpStatus.BAD_REQUEST, "로그인이 필요합니다.")
    , DONT_FIND_USER(HttpStatus.BAD_REQUEST, "해당 사용자를 찾을수 없습니다.")
    , DONT_HAVE_AUTHORITY(HttpStatus.BAD_REQUEST, "해당 서비스를 이용할 권한이 없습니다.")

    , FAIL_SAVE_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "파일저장에 실패했습니다.")
    , FAIL_CREATE_POST(HttpStatus.INTERNAL_SERVER_ERROR, "글작성에 싪패햇습니다")

    ;
    private final HttpStatus httpStatus;
    private final String message;
}
