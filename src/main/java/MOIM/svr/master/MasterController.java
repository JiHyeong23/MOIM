package MOIM.svr.master;

import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.utilities.UtilMethods;
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
    public ResponseEntity getApplies(@PathVariable Long groupId, Pageable pageable) {
        Page<ApplyDetailDto> page = masterService.getApplies(groupId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    //가입신청 상세조회
    @GetMapping("/apply/{applyId}")
    public ResponseEntity getApply(@PathVariable Long groupId, @PathVariable Long applyId) {
        ApplyDetailDto applyDetail = masterService.getApplyDetail(applyId);
        return ResponseEntity.status(HttpStatus.OK).body(applyDetail);
    }

    //가입신청 승인
    @PostMapping("/apply/{applyId}")
    public ResponseEntity getMember(@PathVariable Long groupId, @PathVariable Long applyId) {
        masterService.acceptMember(groupId, applyId);
        return ResponseEntity.status(HttpStatus.OK).body("Accept created successfully");
    }

}
