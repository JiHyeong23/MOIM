package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.group.groupDto.MyGroupListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "maxSize", target = "maxSize")
    @Mapping(source = "intro", target = "intro")
    @Mapping(source = "groupImage", target = "groupImage")
    Group groupPostDtoToGroup(GroupPostDto groupPostDto);

    @Mapping(source = "groupName",target = "groupName")
    @Mapping(source = "maxSize", target = "maxSize")
    @Mapping(source = "currentSize", target = "currentSize")
    @Mapping(source = "groupImage", target = "groupImage")
    @Mapping(source = "groupCategory", target = "groupCategory")
    GroupListDto groupToGroupListDto(Group group);

    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "maxSize", target = "maxSize")
    @Mapping(source = "currentSize", target = "currentSize")
    @Mapping(source = "score", target = "score")
    MyGroupListDto groupToMyGroupListDto(Group group);
}
