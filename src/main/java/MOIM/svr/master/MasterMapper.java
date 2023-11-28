package MOIM.svr.master;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MasterMapper {
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "groupId", target = "group.groupId")
    Master toMaster(MasterCreateDao masterCreateDao);
}
