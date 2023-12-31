package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.apply.applyDto.MyApplyListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
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

    @Mapping(source = "reason", target = "reason")
    MyApplyListDto applyToMyList(Apply apply);
}
