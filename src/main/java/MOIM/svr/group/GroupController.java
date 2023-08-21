package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPostDto;
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
    public ResponseEntity<String> postGroup(@RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        groupService.createGroup(groupPostDto, request);
        return ResponseEntity.status(HttpStatus.OK).body("Group created successfully");
    }

}
