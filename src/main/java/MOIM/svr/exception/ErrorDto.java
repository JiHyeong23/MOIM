package MOIM.svr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ErrorDto {
    private ErrorCode errorCode = ErrorCode.NOT_VALID;
    private HttpStatus httpStatus;
    private String message;

    public ErrorDto(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
