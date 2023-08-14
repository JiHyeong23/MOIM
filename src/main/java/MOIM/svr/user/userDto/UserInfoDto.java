package MOIM.svr.user.userDto;

import MOIM.svr.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    private String userNickname;
    private String DOB;
    private String intro;
    private String userImage;
    private List<Post> posts;

    public UserInfoDto updatePost(List<Post> posts) {
        this.posts = posts;
        return this;
    }
}
