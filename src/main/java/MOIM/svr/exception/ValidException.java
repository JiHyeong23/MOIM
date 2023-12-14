package MOIM.svr.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ValidException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;
}
