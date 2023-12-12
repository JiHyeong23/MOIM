package MOIM.svr.apply;

import MOIM.svr.UserGroup.UserGroupRepository;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.apply.applyDto.MyApplyListDto;
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
    private final UserGroupRepository userGroupRepository;

    //가입 신청
    @PostMapping
    public ResponseEntity postApply(@RequestBody ApplyPostDto applyPostDto, HttpServletRequest request) {
        Apply apply = applyService.askApply(applyPostDto, request);
        Group group = groupRepository.findById(applyPostDto.getGroupId()).get();

        ResponseDto response;
        if (group.getCurrentSize() == group.getMaxSize()) {
            response = ResponseDto.builder()
                    .result(Result.FAIL).httpStatus(HttpStatus.IM_USED).memo("Group is full")
                    .response(group.getGroupName()).build();
        } else {
            if (userGroupRepository.findByUserAndGroup(apply.getUser(), group) == null) {
                response = ResponseDto.builder()
                        .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Apply created successfully")
                        .response(group.getGroupName()).build();
            } else {
                response = ResponseDto.builder()
                        .result(Result.FAIL).httpStatus(HttpStatus.IM_USED).memo("Already joined group")
                        .response(group.getGroupName()).build();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저별 가입 신청 조회
    @GetMapping("/my")
    public ResponseEntity getUsersApply(HttpServletRequest request, Pageable pageable) {
        Page<MyApplyListDto> myApply = applyService.findMyApply(request, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("get my apply successfully")
                .response(myApply).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
