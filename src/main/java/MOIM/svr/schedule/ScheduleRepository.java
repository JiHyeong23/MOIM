package MOIM.svr.schedule;

import MOIM.svr.group.Group;
import MOIM.svr.schedule.ScheduleDto.ScheduleDetailDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.groupId = :groupId " +
            "AND s.startDate < :end OR s.endDate > :start ORDER BY s.startDate")
    Page<Schedule> findByGroupId(Long groupId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Schedule findFirstByGroupIdOrderByStartDateDesc(Long groupId);
}
