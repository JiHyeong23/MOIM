package MOIM.svr.apply.applyDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApplyDetailDto {
    private Long applyId;
    private String userNickname;
    private String reason;
    private LocalDateTime createdAt;
    private Boolean handled;
}
