package MOIM.svr.post.postDto;

import MOIM.svr.post.enums.PostCategory;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class PostCreationDto {
    private String title;
    private String body;
    @Nullable
    private Long groupId;
    private PostCategory category;
}
