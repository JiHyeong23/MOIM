package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/applies")
@AllArgsConstructor
public class ApplyController {
    private ApplyService applyService;

    //가입 신청
    @PostMapping
    public ResponseEntity postApply(@RequestBody ApplyPostDto applyPostDto, HttpServletRequest request) {
        applyService.supportApply(applyPostDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Apply created successfully");
    }

}
