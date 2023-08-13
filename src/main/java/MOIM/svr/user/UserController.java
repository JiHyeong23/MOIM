package MOIM.svr.user;

import MOIM.svr.user.userDto.UserIntroDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/intro")
    public ResponseEntity<String> patchUserIntro(@RequestBody UserIntroDto userIntroDto, HttpServletRequest request) {
        userService.modifiedUserIntro(userIntroDto, request);
        return ResponseEntity.status(HttpStatus.OK).body("User intro patched successfully");
    }
}
