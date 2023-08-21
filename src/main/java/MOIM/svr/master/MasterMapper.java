package MOIM.svr.master;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MasterMapper {
    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "groupId", target = "group.groupId")
    Master toMaster(MasterCreateDAO masterCreateDAO);
}
