package MOIM.svr.user;

import MOIM.svr.user.userDto.UserSignUpDto;

import java.time.LocalDateTime;

public class UserStub {
    public UserSignUpDto userRegister() {
        return new UserSignUpDto("Test", "test@gmail.com", "password", LocalDateTime.of(1997,11,20,0,1));
    }
}
