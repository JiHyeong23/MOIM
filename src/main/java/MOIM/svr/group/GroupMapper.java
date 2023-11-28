package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "maxSize", target = "maxSize")
    @Mapping(source = "intro", target = "intro")
    @Mapping(source = "groupImage", target = "groupImage")
    @Mapping(source = "category", target = "category")
    Group groupPostDtoToGroup(GroupPostDto groupPostDto);
}
