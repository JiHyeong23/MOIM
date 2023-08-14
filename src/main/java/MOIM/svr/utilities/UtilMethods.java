package MOIM.svr.utilities;

import MOIM.svr.security.JwtHelper;
import MOIM.svr.user.User;
import MOIM.svr.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UtilMethods {
    private UserRepository userRepository;
    private JwtHelper jwtHelper;

    public User parseTokenForUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtHelper.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
    }
}
