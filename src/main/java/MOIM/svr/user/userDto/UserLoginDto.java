package MOIM.svr.user.userDto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @NotNull
    private String email;
    @NotNull
    private String pw;
}
