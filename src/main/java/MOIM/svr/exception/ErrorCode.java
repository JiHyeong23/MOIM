package MOIM.svr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    NOT_YOURS(HttpStatus.NOT_FOUND, "회원정보가 일치하지 않습니다.")
    ,NAME_CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 이름입니다.")
    ,NOT_MASTER(HttpStatus.NOT_FOUND, "권한이 없습니다.")
    ,IS_FULL(HttpStatus.NOT_FOUND, "인원이 다 찼습니다.")
    ,HAVE_MORE(HttpStatus.NOT_FOUND, "현재인원이 설정인원보다 많습니다.")
    ,SAME_SIZE(HttpStatus.CONFLICT, "현재인원과 동일합니다.")
    ,HAVE_USER(HttpStatus.NOT_FOUND, "멤버가 있어 삭제 불가능합니다.")
    ,NOT_MATCH(HttpStatus.NOT_FOUND, "비밀번호가 틀립니다.")
    ,NOT_VALID(HttpStatus.NOT_FOUND, "유효하지 않은 값이 포함되어 있습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
