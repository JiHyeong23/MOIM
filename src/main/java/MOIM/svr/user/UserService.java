package MOIM.svr.user;

import MOIM.svr.security.JwtHelper;
import MOIM.svr.user.userDto.UserIntroDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserMapper userMapper;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private JwtHelper jwtHelper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User
                (user.getEmail(), user.getPw(), true, true, true, true, new ArrayList<>());
    }

    public void registerUser(UserSignUpDto userSignUpDto) {
        User user = userMapper.userSignUpToUser(userSignUpDto);
        user.setPw(encoder.encode(user.getPw()));
        userRepository.save(user);
    }

    public void modifiedUserIntro(UserIntroDto userIntroDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        System.out.println("===");
        System.out.println(token);
        String email = jwtHelper.getEmailFromJwtToken(token);
        User user = userRepository.findByEmail(email);
        String intro = userIntroDto.getIntro();
        user.updateIntro(intro);
        userRepository.save(user);
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

}
