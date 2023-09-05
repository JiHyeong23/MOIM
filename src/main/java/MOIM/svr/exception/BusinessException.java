package MOIM.svr.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final ExceptionCode exceptionCode;
    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public int getHttpCode() {
        return this.exceptionCode.getHttpStatus().value();
    }

    public String getReason() {
        return this.exceptionCode.getMessage();
    }
}
