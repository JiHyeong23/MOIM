package MOIM.svr.user;

import MOIM.svr.user.userDto.UserInfoDto;
import MOIM.svr.user.userDto.UserPatchDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody UserSignUpDto userSignUpDto) {
        userService.registerUser(userSignUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @GetMapping()
    public ResponseEntity<UserInfoDto> getUser(HttpServletRequest request) {
        UserInfoDto userInfo = userService.getUserInfo(request);
        return ResponseEntity.status(HttpStatus.OK).body(userInfo);
    }

    @PatchMapping("/update")
    public ResponseEntity<String> patchUserInfo(@RequestBody UserPatchDto userPatchDto, HttpServletRequest request) {
        userService.modifiedUserInfo(userPatchDto, request);
        return ResponseEntity.status(HttpStatus.OK).body("User patched successfully");
    }
}
