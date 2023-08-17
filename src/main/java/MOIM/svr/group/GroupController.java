package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPostDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private GroupService groupService;

    public ResponseEntity<String> postGroup(@RequestBody GroupPostDto groupPostDto, HttpServletRequest request) {
        groupService.createGroup(groupPostDto, request);
        return ResponseEntity.status(HttpStatus.OK).body("Group created successfully");
    }
}
