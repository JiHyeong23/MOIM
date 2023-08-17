package MOIM.svr.comment.commentDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentMyResponseDto {
    private String body;
    private LocalDateTime createdAt;

    private String PostTitle;
    private String PostUserNickname;
    private int PostCommentCount;
    private LocalDateTime PostCreatedAt;
}
