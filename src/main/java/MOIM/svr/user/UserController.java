package MOIM.svr.user;

import MOIM.svr.group.groupDto.MyGroupListDto;
import MOIM.svr.user.userDto.UserDeleteDto;
import MOIM.svr.user.userDto.UserInfoDto;
import MOIM.svr.user.userDto.UserPatchDto;
import MOIM.svr.user.userDto.UserSignUpDto;
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
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UserSignUpDto userSignUpDto) {
        User user = userService.registerUser(userSignUpDto);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("User created successfully")
                .response(user.getEmail()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저 정보 수정
    @PatchMapping("/update")
    public ResponseEntity patchUserInfo(@RequestBody UserPatchDto userPatchDto, HttpServletRequest request) {
        User user = userService.modifiedUserInfo(userPatchDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("User patched successfully")
                .response(user.getUserNickname()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //유저 정보 조회
    @GetMapping
    public ResponseEntity getUser(HttpServletRequest request) {
        UserInfoDto userInfo = userService.getUserInfo(request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("User information got successfully")
                .response(userInfo).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //가입 그룹 조회
    @GetMapping("/group")
    public ResponseEntity getUsersGroup(HttpServletRequest request, Pageable pageable) {
        Page<MyGroupListDto> myGroup = userService.findMyGroup(request, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("My groups got successfully")
                .response(myGroup).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //회원탈퇴
    @DeleteMapping
    public ResponseEntity deleteUser(@RequestBody UserDeleteDto userDeleteDto, HttpServletRequest request) {
        userService.removeUser(userDeleteDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.NO_CONTENT).memo("User deleted successfully")
                .response("No content").build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
