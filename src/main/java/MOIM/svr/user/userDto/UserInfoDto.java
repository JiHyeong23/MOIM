package MOIM.svr.user.userDto;

import MOIM.svr.post.postDto.MyPostListDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    private Long userId;
    private String userNickname;
    private String email;
    private LocalDateTime dob;
    private String intro;
    private String userImage;
    private List<MyPostListDto> posts;
}
