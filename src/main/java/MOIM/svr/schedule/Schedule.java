package MOIM.svr.schedule;

import MOIM.svr.post.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Getter
@Setter
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    private Long groupId;
    private String scheduleName;
    private String body;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ScheduleStatus status;
    private Boolean highlight;
}
