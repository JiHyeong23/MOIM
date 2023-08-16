package MOIM.svr.post.postDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDto {
    private Long postId;
    private String title;
    private String userNickname;
    private Long userId;
    private LocalDateTime createdAt;
    private Long commentsCount;
}
