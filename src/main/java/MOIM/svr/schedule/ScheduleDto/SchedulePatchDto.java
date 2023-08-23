package MOIM.svr.schedule.ScheduleDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePatchDto {
    private Long scheduleId;
    private String scheduleName;
    private String body;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean hightlight;
}
