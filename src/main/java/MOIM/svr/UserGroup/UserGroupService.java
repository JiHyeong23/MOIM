package MOIM.svr.UserGroup;

import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.user.User;
import MOIM.svr.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserGroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    public void createUserGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId).get();
        User user = userRepository.findById(userId).get();
        UserGroup userGroup = new UserGroup(group, user);
        userGroupRepository.save(userGroup);
    }
}