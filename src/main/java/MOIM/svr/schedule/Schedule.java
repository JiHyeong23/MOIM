package MOIM.svr.schedule;

import MOIM.svr.post.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    private Long groupId;
    private String scheduleName;
    private String body;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Status status;

}
