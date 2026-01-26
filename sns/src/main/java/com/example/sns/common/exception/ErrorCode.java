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
    , FAIL_CREATE_POST(HttpStatus.INTERNAL_SERVER_ERROR, "글작성에 실패햇습니다")
    , FAIL_CREATE_COMMENT(HttpStatus.INTERNAL_SERVER_ERROR, "댓글작성에 실패햇습니다")

    , FAIL_JSON_CONVERT(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류(json변환 실패)")
    // 채팅
    ,FAIL_ROOM_CREATE(HttpStatus.INTERNAL_SERVER_ERROR, "단톡방 생성에 실패했습니다.")
    ,MENTOR_CAN_CREATE_ROOM(HttpStatus.FORBIDDEN, "멘토만이 단톡방을 생성할수 있습니다.")
    ,MENTOR_CAN_END_ROOM(HttpStatus.FORBIDDEN, "멘토만이 단톡방을 종료할수 있습니다.")
    ,ROOM_MENTOR_CAN_END(HttpStatus.FORBIDDEN, "단톡방의 멘토만이 해당방을 종료할수 있습니다.")
    ,FAIL_ENTER_ROOM(HttpStatus.INTERNAL_SERVER_ERROR, "단톡방 입장에 실패했습니다.")
    ,FAIL_EXIT_ROOM(HttpStatus.INTERNAL_SERVER_ERROR, "단톡방 퇴장에 실패했습니다.")
    ,FAIL_END_ROOM(HttpStatus.INTERNAL_SERVER_ERROR, "단톡방 종료에 실패했습니다.")
    ,ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 단톡방입니다.")
    ,ROOM_USER_LIMIT(HttpStatus.BAD_REQUEST, "입장가능한 인원을 초과했습니다.")
    ;
    private final HttpStatus httpStatus;
    private final String message;
}
