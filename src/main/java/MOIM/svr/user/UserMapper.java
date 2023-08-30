package MOIM.svr.user;

import MOIM.svr.user.userDto.UserInfoDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "userNickname", target = "userNickname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "pw", target = "pw")
    @Mapping(source = "dob", target = "dob")
    User userSignUpToUser(UserSignUpDto userSignUpDto);

    @Mapping(source = "userNickname", target = "userNickname")
    @Mapping(source = "dob", target = "dob")
    @Mapping(source = "intro", target = "intro")
    @Mapping(source = "userImage", target = "userImage")
    @Mapping(target = "posts", ignore = true)
    UserInfoDto userToUserInfoDto(User user);
}
