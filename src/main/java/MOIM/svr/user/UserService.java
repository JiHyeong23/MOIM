package MOIM.svr.user;

import MOIM.svr.post.Post;
import MOIM.svr.post.PostRepository;
import MOIM.svr.user.userDto.UserInfoDto;
import MOIM.svr.user.userDto.UserPatchDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import MOIM.svr.utilities.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserMapper userMapper;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private UtilMethods utilMethods;
    private final PostRepository postRepository;

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

    public UserInfoDto getUserInfo(HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(user);
        List<Post> posts = postRepository.findTop3ByUserOrderByCreatedAtDesc(user);
        userInfoDto.updatePost(posts);
        return userInfoDto;
    }

    public void modifiedUserInfo(UserPatchDto userPatchDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);

        String intro = userPatchDto.getIntro();
        if (intro != null) {
            user.updateIntro(intro);
        }
        String nickname = userPatchDto.getNickname();
        if(nickname != null) {
            user.updateNickname(nickname);
        }
        String userImage = userPatchDto.getUserImage();
        if(userImage != null) {
            user.updateUserImage(userImage);
        }

        userRepository.save(user);
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

}
