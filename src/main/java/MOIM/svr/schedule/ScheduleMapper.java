package MOIM.svr.schedule;

import MOIM.svr.schedule.ScheduleDto.ScheduleDetailDto;
import MOIM.svr.schedule.ScheduleDto.SchedulePostDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {

    @Mapping(source = "scheduleName", target = "scheduleName")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "highlight", target = "highlight")
    @Mapping(target = "groupId", ignore = true)
    @Mapping(target = "status", ignore = true)
    Schedule schedulePostDtoToSchedule(SchedulePostDto schedulePostDto);

    @Mapping(source = "scheduleName", target = "scheduleName")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "highlight", target = "highlight")
    ScheduleDetailDto scheduleToScheduleDetailDto(Schedule schedule);
}
