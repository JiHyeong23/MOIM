package MOIM.svr.post.postDto;

import MOIM.svr.post.enums.PostCategory;
import MOIM.svr.post.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostDetailDto {
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private Long groupId;
    private Status status;
    private PostCategory category;
    private String commentCount;
    private Long userId;

}
