package MOIM.svr.post.postDto;

import MOIM.svr.utilities.Category;
import lombok.Getter;

@Getter
public class PostCreationDto {
    private String title;
    private String body;
    private Long groupId;
    private Category category;
}
