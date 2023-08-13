package MOIM.svr.user;

import MOIM.svr.user.userDto.UserSignUpDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "userNickname", target = "userNickname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "pw", target = "pw")
    @Mapping(source = "DOB", target = "DOB")
    User userSignUpToUser(UserSignUpDto userSignUpDto);
}
