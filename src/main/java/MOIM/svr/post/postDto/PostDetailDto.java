package MOIM.svr.post.postDto;

import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.post.enums.PostCategory;
import MOIM.svr.post.enums.Status;
import MOIM.svr.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailDto {
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private Long groupId;
    private Status status;
    private PostCategory category;
    private String userNickname;
    private List<CommentResponseDto> comments;
    private Long commentsCount;
}
