package MOIM.svr.user;

import MOIM.svr.UserGroup.UserGroup;
import MOIM.svr.UserGroup.UserGroupRepository;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupMapper;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.group.groupDto.MyGroupListDto;
import MOIM.svr.post.Post;
import MOIM.svr.post.PostRepository;
import MOIM.svr.post.postDto.MyPostListDto;
import MOIM.svr.schedule.ScheduleRepository;
import MOIM.svr.user.userDto.UserDeleteDto;
import MOIM.svr.user.userDto.UserInfoDto;
import MOIM.svr.user.userDto.UserPatchDto;
import MOIM.svr.user.userDto.UserSignUpDto;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final GroupRepository groupRepository;
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
        User user = userMapper.userSignUpToUser(userSignUpDto);
        user.setPw(encoder.encode(user.getPw()));
        userRepository.save(user);
        return user;
    }

    public UserInfoDto getUserInfo(HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        UserInfoDto userInfoDto = userMapper.userToUserInfoDto(user);
        Long userId = user.getUserId();
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
            user.updateNickname(nickname);
        }
        String userImage = userPatchDto.getUserImage();
        if(userImage != null) {
            user.updateUserImage(userImage);
        }

        userRepository.save(user);
        return user;
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

    public void removeUser(UserDeleteDto userDeleteDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        userRepository.delete(user);
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

}
