package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPostDto;
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
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private GroupService groupService;
    private UtilMethods utilMethods;

    //그룹 생성
    @PostMapping
    public ResponseEntity postGroup(@RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        Group group = groupService.createGroup(groupPostDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                group.getGroupName(), HttpStatus.CREATED, "Group created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //그룹 목록 조회
    @GetMapping
    public ResponseEntity getGroupList(@RequestParam(value = "pageNo") int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<GroupListDto> groups = groupService.getGroups(pageable);
        List<GroupListDto> content = groups.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "Groups got successfully", pageNo, groups);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
