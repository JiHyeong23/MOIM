package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPatchDto;
import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
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

}
