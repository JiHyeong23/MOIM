package MOIM.svr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static MOIM.svr.exception.ErrorCode.NOT_YOURS;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({CustomException.class})
    protected ResponseEntity handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new ErrorDto(e.getErrorCode(), e.getErrorCode().getStatus(), e.getErrorCode().getMessage()));
    }

    @ExceptionHandler({ValidException.class})
    protected ResponseEntity handledValidException(ValidException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorDto(e.getHttpStatus(), e.getMessage()));
    }
}
