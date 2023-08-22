package MOIM.svr.post.postDto;

import MOIM.svr.utils.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostCreationDto {
    private String title;
    private String body;
    private Long groupId;
    private Category category;
}
