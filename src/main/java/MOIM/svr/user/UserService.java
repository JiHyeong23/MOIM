package MOIM.svr.user;

import MOIM.svr.exception.CustomException;
import MOIM.svr.exception.ErrorCode;
import MOIM.svr.mail.EmailController;
import MOIM.svr.user.userDto.*;
import MOIM.svr.userGroup.UserGroup;
import MOIM.svr.userGroup.UserGroupRepository;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupMapper;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.group.groupDto.MyGroupListDto;
import MOIM.svr.post.PostRepository;
import MOIM.svr.post.postDto.MyPostListDto;
import MOIM.svr.schedule.ScheduleRepository;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final GroupMapper groupMapper;
    private final ScheduleRepository scheduleRepository;
    private final UserGroupRepository userGroupRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username);
            return new org.springframework.security.core.userdetails.User
                    (user.getEmail(), user.getPw(), true, true, true, true, new ArrayList<>());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다");
        }
    }

    public User registerUser(UserSignUpDto userSignUpDto) {
        User user;
        if (userRepository.findByUserNickname(userSignUpDto.getUserNickname()) != null) {
            throw new CustomException(ErrorCode.NAME_CONFLICT);
        } else {
            user = userMapper.userSignUpToUser(userSignUpDto);
            user.setPw(encoder.encode(user.getPw()));
            userRepository.save(user);
        }
        return user;
    }

    public UserInfoDto getUserInfo(HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(user);
        Long userId = user.getUserId();
        userInfoDto.setUserId(userId);
        List<MyPostListDto> posts = postRepository.findMyPostListDto(userId);
        userInfoDto.setPosts(posts);
        return userInfoDto;
    }

    public User modifiedUserInfo(UserPatchDto userPatchDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);

        String intro = userPatchDto.getIntro();
        if (intro != null) {
            user.updateIntro(intro);
        }
        String nickname = userPatchDto.getNickname();
        if(nickname != null) {
            if (userRepository.findByUserNickname(userPatchDto.getNickname()) != null) {
                throw new CustomException(ErrorCode.NAME_CONFLICT);
            } else {
                user.updateNickname(nickname);
            }
        }
        String userImage = userPatchDto.getUserImage();
        if(userImage != null) {
            user.updateUserImage(userImage);
        }

        userRepository.save(user);
        return user;
    }

    public void removeUser(UserDeleteDto userDeleteDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        if (encoder.matches(userDeleteDto.getPw(),user.getPw())) {
            userRepository.delete(user);
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH);
        }
    }

    public Page<MyGroupListDto> findMyGroup(HttpServletRequest request, Pageable pageable) {
        User user = utilMethods.parseTokenForUser(request);
        Page<UserGroup> groups = userGroupRepository.findByUser(user, pageable);

        return groups.map(userGroup -> {
            Group group = userGroup.getGroup();
            MyGroupListDto myGroupListDto = groupMapper.groupToMyGroupListDto(group);
            try {
                myGroupListDto.setPost(postRepository.findFirstByGroupOrderByCreatedAtDesc(group).getTitle());
            } catch (Exception e) {
                myGroupListDto.setPost("-");
            }
            try {
                myGroupListDto.setFirstMeetTime(
                        scheduleRepository.findFirstByGroupIdOrderByStartDateDesc(group.getGroupId()).getScheduleName());
            } catch (Exception e) {
                myGroupListDto.setFirstMeetTime("-");
            }
            return myGroupListDto;
        });
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

    public void setTmpPassword(String email, String tmpPw) {
        User user = userRepository.findByEmail(email);
        user.setPw(encoder.encode(tmpPw));
        userRepository.save(user);
    }

    public User modifyPassword(UserPwDto userPwDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        if (encoder.matches(userPwDto.getBeforePw(), user.getPw())) {
            user.setPw(encoder.encode(userPwDto.getSetPw()));
        } else {
            throw new CustomException(ErrorCode.NOT_MATCH);
        }
        return user;
    }
}
