package MOIM.svr.post.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
