package MOIM.svr.post.postDto;

import MOIM.svr.utils.Category;
import lombok.Getter;

@Getter
public class PostPatchDto {
    private Long postId;
    private String title;
    private String body;
    private Category category;
}
