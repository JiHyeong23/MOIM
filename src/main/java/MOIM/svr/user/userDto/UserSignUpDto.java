package MOIM.svr.user.userDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class UserSignUpDto {
    private String userNickname;
    private String email;
    private String pw;
    private String DOB;
}
