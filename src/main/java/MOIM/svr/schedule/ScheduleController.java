package MOIM.svr.schedule;

import MOIM.svr.schedule.ScheduleDto.ScheduleDetailDto;
import MOIM.svr.utils.PageResponseDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules/{groupId}")
@AllArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;
    private UtilMethods utilMethods;

    //스케줄 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId) {
        ScheduleDetailDto schedule = scheduleService.getScheduleDetail(scheduleId);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                schedule, "Schedule got successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //월별 스케줄 조회
    @GetMapping("/list/{date}")
    public ResponseEntity getDaySchedules(@PathVariable Long groupId, @PathVariable String date, @RequestParam(value = "pageNo") int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<ScheduleDetailDto> schedules = scheduleService.getMonthSchedules(groupId, date, pageable);
        List<ScheduleDetailDto> content = schedules.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "Schedules got successfully", pageNo, schedules);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
