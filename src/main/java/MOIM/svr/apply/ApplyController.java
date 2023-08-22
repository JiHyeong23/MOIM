package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/applies")
@AllArgsConstructor
public class ApplyController {
    private ApplyService applyService;
    private GroupRepository groupRepository;

    //가입 신청
    @PostMapping
    public ResponseEntity postApply(@RequestBody ApplyPostDto applyPostDto, HttpServletRequest request) {
        Apply apply = applyService.askApply(applyPostDto, request);
        Group group = groupRepository.findById(applyPostDto.getGroupId()).get();
        //그룹 조회 없이 가져오기(연관관계로) 나중에 수정
        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Apply created successfully")
                .response(group.getGroupName()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
