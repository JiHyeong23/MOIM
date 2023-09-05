package MOIM.svr.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003"),
    DUPLICATED(HttpStatus.IM_USED, "U001", "이미 존재하는 정보입니다"),
    USER_EXIST(HttpStatus.CONFLICT, "이미 존재하는 회원입니다."),
    SECURITY_01(HttpStatus.UNAUTHORIZED, "S0001", "권한이 없습니다.")
    ;

    private final HttpStatus status;
    private final String code;
    private String message;

    ExceptionEnum(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    ExceptionEnum(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
