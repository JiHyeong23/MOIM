package MOIM.svr.user.userDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class UserSignUpDto {
    private String userNickname;
    private String email;
    private String pw;
    private LocalDateTime dob;
}
