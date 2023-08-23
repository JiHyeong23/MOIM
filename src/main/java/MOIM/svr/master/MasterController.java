package MOIM.svr.master;

import MOIM.svr.apply.Apply;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.schedule.Schedule;
import MOIM.svr.schedule.ScheduleDto.SchedulePatchDto;
import MOIM.svr.schedule.ScheduleDto.SchedulePostDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/master/{groupId}")
@AllArgsConstructor
public class MasterController {
    private MasterService masterService;

    //그룹별 가입 신청 목록 조회
    @GetMapping("/apply")
    public ResponseEntity getApplies(@PathVariable Long groupId, Pageable pageable, HttpServletRequest request) {
        Page<ApplyDetailDto> page = masterService.getApplies(groupId, pageable, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Applies got successfully")
                .response(page).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 상세조회
    @GetMapping("/apply/{applyId}")
    public ResponseEntity getApply(@PathVariable Long groupId, @PathVariable Long applyId, HttpServletRequest request) {
        ApplyDetailDto applyDetail = masterService.getApplyDetail(groupId, applyId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Apply got successfully")
                .response(applyDetail).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 승인
    @PostMapping("/apply/{applyId}")
    public ResponseEntity getMember(@PathVariable Long groupId, @PathVariable Long applyId, HttpServletRequest request) {
        Apply apply = masterService.acceptMember(groupId, applyId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Accept created successfully")
                .response(apply.getHandled().toString()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 생성
    @PostMapping("/schedule")
    public ResponseEntity postSchedule(@PathVariable Long groupId, @RequestBody SchedulePostDto schedulePostDto, HttpServletRequest request) {
        Schedule schedule = masterService.createSchedule(groupId, schedulePostDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Schedule created successfully")
                .response(schedule.getScheduleName()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 수정
    @PatchMapping("/schedule")
    public ResponseEntity patchSchedule(@PathVariable Long groupId, @RequestBody SchedulePatchDto schedulePatchDto, HttpServletRequest request) {
        Schedule schedule = masterService.modifySchedule(groupId, schedulePatchDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Schedule modified successfully")
                .response(schedule.getScheduleName()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId, HttpServletRequest request) {
        masterService.deleteSchedule(groupId, scheduleId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.NO_CONTENT)
                .response(scheduleId).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //글 공지설정
    @PostMapping("/post/{postId}")
    public ResponseEntity postSetNotice(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        masterService.postSetNotice(groupId, postId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Post set notice successfully")
                .response(postId).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
