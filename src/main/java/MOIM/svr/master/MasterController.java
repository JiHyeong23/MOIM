package MOIM.svr.master;

import MOIM.svr.apply.Apply;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.groupDto.GroupPatchDto;
import MOIM.svr.schedule.Schedule;
import MOIM.svr.schedule.ScheduleDto.SchedulePatchDto;
import MOIM.svr.schedule.ScheduleDto.SchedulePostDto;
import MOIM.svr.utils.PageResponseDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static MOIM.svr.utils.PageResponseDto.*;

@RestController
@RequestMapping("/master/{groupId}")
@AllArgsConstructor
public class MasterController {
    private MasterService masterService;
    private UtilMethods utilMethods;

    //그룹별 가입 신청 목록 조회
    @GetMapping("/apply")
    public ResponseEntity getApplies(@PathVariable Long groupId, @RequestParam(value = "pageNo") int pageNo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<ApplyDetailDto> page = masterService.getApplies(groupId, pageable, request);
        List<ApplyDetailDto> content = page.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "Applies got successfully", pageNo, page);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 상세조회
    @GetMapping("/apply/{applyId}")
    public ResponseEntity getApply(@PathVariable Long groupId, @PathVariable Long applyId, HttpServletRequest request) {
        ApplyDetailDto applyDetail = masterService.getApplyDetail(groupId, applyId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                applyDetail, "Apply got successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 승인
    @PostMapping("/apply/{applyId}")
    public ResponseEntity getMember(@PathVariable Long groupId, @PathVariable Long applyId, HttpServletRequest request) {
        Apply apply = masterService.acceptMember(groupId, applyId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                apply.getHandled().toString(), HttpStatus.CREATED, "Accept created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 거절
    @DeleteMapping("/apply/{applyId}")
    public ResponseEntity rejectMember(@PathVariable Long groupId, @PathVariable Long applyId, HttpServletRequest request) {
        Apply apply = masterService.rejectMember(groupId, applyId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                apply.getHandled(), HttpStatus.NO_CONTENT, "Apply deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 생성
    @PostMapping("/schedule")
    public ResponseEntity postSchedule(@PathVariable Long groupId, @RequestBody SchedulePostDto schedulePostDto, HttpServletRequest request) {
        Schedule schedule = masterService.createSchedule(groupId, schedulePostDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                schedule.getScheduleName(), HttpStatus.CREATED, "Schedule created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 수정
    @PatchMapping("/schedule")
    public ResponseEntity patchSchedule(@PathVariable Long groupId, @RequestBody SchedulePatchDto schedulePatchDto, HttpServletRequest request) {
        Schedule schedule = masterService.modifySchedule(groupId, schedulePatchDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                schedule.getScheduleName(), "Schedule modified successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //스케줄 삭제
    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable Long groupId, @PathVariable Long scheduleId, HttpServletRequest request) {
        masterService.deleteSchedule(groupId, scheduleId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                scheduleId, HttpStatus.NO_CONTENT, "Schedule deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //글 공지설정
    @PostMapping("/post/{postId}")
    public ResponseEntity postSetNotice(@PathVariable Long groupId, @PathVariable Long postId, HttpServletRequest request) {
        masterService.postSetNotice(groupId, postId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                postId, "Post set notice successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //그룹 정보 수정
    @PatchMapping("/info")
    public ResponseEntity patchGroup(@PathVariable Long groupId, @RequestBody GroupPatchDto groupPatchDto, HttpServletRequest request) {
        Group group = masterService.modifyGroupInfo(groupId, groupPatchDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                group.getGroupName(), "Group info modified successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //그룹 인원 수정
    @PatchMapping("/number/{modifyNumber}")
    public ResponseEntity patchNumber(@PathVariable Long groupId, @PathVariable Long modifyNumber, HttpServletRequest request) {
        Group group = masterService.modifyGroupNumber(groupId, modifyNumber, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                group.getMaxSize(), "Group number modified successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //그룹 삭제
    @DeleteMapping
    public ResponseEntity deleteGroup(@PathVariable Long groupId, HttpServletRequest request) {
        masterService.deleteGroup(groupId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                groupId, HttpStatus.NO_CONTENT, "Group deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
