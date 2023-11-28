package MOIM.svr.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse<T> {
    private int code;
    private String message;
    private String reason;
    private T errors;

    @Getter
    @Builder
    public static class ValidationError {
        private String field;
        private String message;

        public static ValidationError of(FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
