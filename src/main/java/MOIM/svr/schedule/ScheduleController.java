package MOIM.svr.schedule;

import MOIM.svr.schedule.ScheduleDto.ScheduleDetailDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules/{groupId}")
@AllArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;

    //스케줄 조회
    @GetMapping("/{scheduleId}")
    public ResponseEntity getSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId) {
        ScheduleDetailDto schedule = scheduleService.getScheduleDetail(scheduleId);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Schedule got successfully")
                .response(schedule).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //월별 스케줄 조회
    @GetMapping("/list/{date}")
    public ResponseEntity getDaySchedules(@PathVariable Long groupId, @PathVariable String date, Pageable pageable) {
        Page<ScheduleDetailDto> schedules = scheduleService.getMonthSchedules(groupId, date, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Schedules got successfully")
                .response(schedules).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
