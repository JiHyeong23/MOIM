package MOIM.svr.post.postDto;

import MOIM.svr.post.enums.PostCategory;
import lombok.Getter;

@Getter
public class PostCreationDto {
    private String title;
    private String body;
    private Long groupId;
    private PostCategory category;
}
