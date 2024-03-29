package MOIM.svr.user;

import MOIM.svr.exception.CustomException;
import MOIM.svr.exception.ErrorCode;
import MOIM.svr.exception.ValidException;
import MOIM.svr.group.groupDto.MyGroupListDto;
import MOIM.svr.user.userDto.*;
import MOIM.svr.utils.PageResponseDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UtilMethods utilMethods;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody @Valid UserSignUpDto userSignUpDto, Errors errors) {
        if (errors.hasErrors()) {
            for (FieldError error : errors.getFieldErrors()) {
                throw new ValidException(HttpStatus.NOT_FOUND, error.getDefaultMessage());
            }
            throw new CustomException(ErrorCode.NOT_VALID);
        }
        User user = userService.registerUser(userSignUpDto);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                user.getEmail(), "User created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저 정보 수정
    @PatchMapping("/update")
    public ResponseEntity patchUserInfo(@RequestBody UserPatchDto userPatchDto, HttpServletRequest request) {
        User user = userService.modifiedUserInfo(userPatchDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                user.getUserNickname(), "User patched successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저 정보 조회
    @GetMapping
    public ResponseEntity getUser(HttpServletRequest request) {
        UserInfoDto userInfo = userService.getUserInfo(request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                userInfo, "User information got successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입 그룹 조회
    @GetMapping("/group")
    public ResponseEntity getUsersGroup(@RequestParam(value = "pageNo") int pageNo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<MyGroupListDto> myGroup = userService.findMyGroup(request, pageable);
        List<MyGroupListDto> content = myGroup.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "My groups got successfully", pageNo, myGroup);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //비밀번호 변경
    @PostMapping("/pw")
    public ResponseEntity modifyUserPw(@RequestBody UserPwDto userPwDto, HttpServletRequest request) {
        User user = userService.modifyPassword(userPwDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                user.getUserId(), HttpStatus.OK, "Password modified successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //회원탈퇴
    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody UserDeleteDto userDeleteDto, HttpServletRequest request) {
        userService.removeUser(userDeleteDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                "No content", HttpStatus.NO_CONTENT, "User deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
