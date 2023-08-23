package MOIM.svr.schedule.ScheduleDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePostDto {
    private String scheduleName;
    private String body;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean highlight;
}
