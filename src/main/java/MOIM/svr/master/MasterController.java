package MOIM.svr.master;

import MOIM.svr.apply.Apply;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/master/{groupId}")
@AllArgsConstructor
public class MasterController {
    private MasterService masterService;

    //그룹별 가입 신청 목록 조회
    @GetMapping("/apply")
    public ResponseEntity getApplies(@PathVariable Long groupId, Pageable pageable) {
        Page<ApplyDetailDto> page = masterService.getApplies(groupId, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Applies got successfully")
                .response(page).build();
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    //가입신청 상세조회
    @GetMapping("/apply/{applyId}")
    public ResponseEntity getApply(@PathVariable Long groupId, @PathVariable Long applyId) {
        ApplyDetailDto applyDetail = masterService.getApplyDetail(applyId);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Apply got successfully")
                .response(applyDetail).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입신청 승인
    @PostMapping("/apply/{applyId}")
    public ResponseEntity getMember(@PathVariable Long groupId, @PathVariable Long applyId) {
        Apply apply = masterService.acceptMember(groupId, applyId);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Accept created successfully")
                .response(apply.getHandled().toString()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
