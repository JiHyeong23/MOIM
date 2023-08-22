package MOIM.svr.user.userDto;

import MOIM.svr.post.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    private String userNickname;
    private LocalDateTime dob;
    private String intro;
    private String userImage;
    private List<Post> posts;
}
