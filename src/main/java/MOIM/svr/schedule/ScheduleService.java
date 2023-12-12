package MOIM.svr.schedule;

import MOIM.svr.schedule.ScheduleDto.ScheduleDetailDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private ScheduleMapper scheduleMapper;

    public ScheduleDetailDto getScheduleDetail(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).get();
        schedule.setStatus(calculateStatus(schedule));
        return scheduleMapper.scheduleToScheduleDetailDto(schedule);
    }

    public Page<ScheduleDetailDto> getMonthSchedules(Long groupId, String date, Pageable pageable) {
        LocalDate requestMonth = LocalDate.parse(date);
        int year = requestMonth.getYear();
        int month = requestMonth.getMonthValue();
        YearMonth toMonth = YearMonth.of(year,month);
        int length = toMonth.lengthOfMonth();
        LocalDateTime start = LocalDateTime.of(year, month, 1,0,0,0);
        LocalDateTime end = LocalDateTime.of(year, month, length, 0,0,0);

        Page<Schedule> schedules = scheduleRepository.findByGroupId(groupId, start, end, pageable);

        return schedules.map(schedule -> {
            schedule.setStatus(calculateStatus(schedule));
            return scheduleMapper.scheduleToScheduleDetailDto(schedule);
        });
    }

    public ScheduleStatus calculateStatus(Schedule schedule) {
        LocalDateTime startDate = schedule.getStartDate();
        LocalDateTime endDate = schedule.getEndDate();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(startDate)) {
            return ScheduleStatus.PREVIOUS;
        }
        else if (now.isAfter(startDate) && now.isBefore(endDate)) {
            return ScheduleStatus.INPROGRESS;
        }
        else {
            return ScheduleStatus.FINISHED;
        }
    }

}
