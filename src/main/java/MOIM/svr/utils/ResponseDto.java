package MOIM.svr.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ResponseDto<T> {
    private Result result;
    private HttpStatus httpStatus;
    private String memo;
    private T response;
}
