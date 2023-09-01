package MOIM.svr.post.postDto;

import MOIM.svr.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyPostListDto {
    private Long userId;
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private long commentCount;
}
