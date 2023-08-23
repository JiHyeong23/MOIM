package MOIM.svr.schedule.ScheduleDto;

import MOIM.svr.schedule.ScheduleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScheduleDetailDto {
    private String scheduleName;
    private String body;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleStatus status;
    private Boolean highlight;
}
