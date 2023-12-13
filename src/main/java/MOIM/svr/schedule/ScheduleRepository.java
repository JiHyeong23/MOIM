package MOIM.svr.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s FROM Schedule s WHERE s.groupId = :groupId " +
        "AND s.startDate BETWEEN :start AND :end OR s.endDate BETWEEN :start AND :end")
    Page<Schedule> findByGroupId(Long groupId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Schedule findFirstByGroupIdOrderByStartDateDesc(Long groupId);
}
