package MOIM.svr.apply.applyDto;

import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class ApplyListDto {
    private String userNickname;
    private LocalDateTime createdAt;
}
