package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPatchDto;
import MOIM.svr.group.groupDto.GroupPostDto;
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
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private GroupService groupService;

    //그룹 생성
    @PostMapping
    public ResponseEntity postGroup(@RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        Group group = groupService.createGroup(groupPostDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Group created successfully")
                .response(group.getGroupName()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //그룹 목록 조회
    @GetMapping
    public ResponseEntity getGroupList(Pageable pageable) {
        Page<GroupListDto> groups = groupService.getGroups(pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Groups got successfully")
                .response(groups).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
