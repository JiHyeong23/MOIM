package MOIM.svr.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class PageResponseDto<T> {
    private Result result;
    private HttpStatus httpStatus;
    private String memo;
    private T response;
    private PageInfo pageInfo;

    @Builder
    @Getter
    public static class PageInfo {
        private int pageNo;
        private int pageSize;
        private long totalElements;
        private int totalPages;
        private boolean last;
    }
}
