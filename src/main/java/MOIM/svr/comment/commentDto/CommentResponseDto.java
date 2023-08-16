package MOIM.svr.comment.commentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
    private String body;
    private LocalDateTime createdAt;
    private Long userId;
    private String userNickname;

}
