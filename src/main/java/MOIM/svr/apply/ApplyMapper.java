package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplyMapper {

    @Mapping(source = "groupId", target = "group.groupId")
    @Mapping(source = "reason", target = "reason")
    @Mapping(target = "applyId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "handledAt", ignore = true)
    @Mapping(target = "handled", ignore = true)
    Apply applyPostDtoToApply(ApplyPostDto applyPostDto);

    @Mapping(source = "applyId", target = "applyId")
    @Mapping(source = "user.userNickname", target = "userNickname")
    @Mapping(source = "reason", target = "reason")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "handled", target = "handled")
    ApplyDetailDto applyToApplyDetailDto(Apply apply);
}
