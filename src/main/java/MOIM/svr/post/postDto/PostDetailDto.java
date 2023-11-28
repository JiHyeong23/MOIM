package MOIM.svr.post.postDto;

import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.utils.Category;
import MOIM.svr.post.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailDto {
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private Long groupId;
    private Status status;
    private Category category;
    private String userNickname;
    private List<CommentResponseDto> comments;
    private Long commentsCount;
    private Boolean notice;
}
