package MOIM.svr.mail;

import MOIM.svr.user.User;
import MOIM.svr.user.UserService;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/mail")
@RestController
@AllArgsConstructor
public class EmailController {
    private EmailService emailService;
    private UserService userService;
    private UtilMethods utilMethods;

    @GetMapping("/password")
    public ResponseEntity sendMail(HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(user.getEmail()).subject("[MOIM] 임시 비밀번호").build();
        String password = emailService.sendMail(emailMessage, "password");

        userService.setTmpPassword(user.getEmail(), password);
        ResponseDto responseDto = utilMethods.makeSuccessResponseDto(user.getUserId(), "tempPassword send successfully");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
