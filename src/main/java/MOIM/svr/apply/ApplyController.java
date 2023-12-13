package MOIM.svr.apply;

import MOIM.svr.UserGroup.UserGroupRepository;
import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.apply.applyDto.MyApplyListDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/applies")
@AllArgsConstructor
public class ApplyController {
    private ApplyService applyService;
    private GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private UtilMethods utilMethods;

    //가입 신청
    @PostMapping
    public ResponseEntity postApply(@RequestBody ApplyPostDto applyPostDto, HttpServletRequest request) {
        Apply apply = applyService.askApply(applyPostDto, request);
        Group group = groupRepository.findById(applyPostDto.getGroupId()).get();

        ResponseDto response;
        if (group.getCurrentSize() == group.getMaxSize()) {
            response = utilMethods.makeFailResponseDto(
                    group.getGroupName(), "Group is full");
        } else {
            if (userGroupRepository.findByUserAndGroup(apply.getUser(), group) == null) {
                response = utilMethods.makeSuccessResponseDto(
                        group.getGroupName(), HttpStatus.CREATED, "Apply created successfully");
            } else {
                response = utilMethods.makeFailResponseDto(
                        group.getGroupName(), "Already joined group");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저별 가입 신청 조회
    @GetMapping("/my")
    public ResponseEntity getUsersApply(@RequestParam(value = "pageNo") int pageNo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<MyApplyListDto> myApply = applyService.findMyApply(request, pageable);
        List<MyApplyListDto> content = myApply.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "get my apply successfully", pageNo, myApply);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
