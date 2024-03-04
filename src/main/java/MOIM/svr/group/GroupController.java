package MOIM.svr.group;

import MOIM.svr.exception.CustomException;
import MOIM.svr.exception.ErrorCode;
import MOIM.svr.exception.ValidException;
import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.utils.PageResponseDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {
    private GroupService groupService;
    private UtilMethods utilMethods;

    //그룹 생성
    @PostMapping
    public ResponseEntity postGroup(@RequestBody @Valid GroupPostDto groupPostDto, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new ValidException(HttpStatus.CONFLICT, error.getDefaultMessage());
            }
            throw new CustomException(ErrorCode.NOT_VALID);
        }
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

    @PostMapping("/{moimId}")
    public ResponseEntity syncGroupSize(@PathVariable Long moimId) {
        int size = groupService.synchronizeSize(moimId);
        ResponseDto responseDto;
        if (size == 0) {
            responseDto = utilMethods.makeFailResponseDto(moimId, "already updated");
        } else {
            responseDto = utilMethods.makeSuccessResponseDto(
                    size, "Successfully updated"
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
