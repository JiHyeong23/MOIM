package MOIM.svr.group;

import MOIM.svr.exception.CustomException;
import MOIM.svr.exception.ErrorCode;
import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.master.MasterCreateDao;
import MOIM.svr.master.MasterService;
import MOIM.svr.user.User;
import MOIM.svr.userGroup.UserGroup;
import MOIM.svr.userGroup.UserGroupRepository;
import MOIM.svr.userGroup.UserGroupService;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;
    private UtilMethods utilMethods;
    private MasterService masterService;
    private UserGroupService userGroupService;
    private final UserGroupRepository userGroupRepository;

    public Group createGroup(GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group;
        if(groupRepository.findByGroupName(groupPostDto.getGroupName()) != null) {
            throw new CustomException(ErrorCode.NAME_CONFLICT);
        } else {
            group = groupMapper.groupPostDtoToGroup(groupPostDto);
            group.setGroupCategory(groupPostDto.getGroupCategory());
            group.setCreatedAt(LocalDateTime.now());
            group.setCurrentSize(1);
            groupRepository.save(group);

            userGroupService.createUserGroup(group.getGroupId(), user.getUserId());

            MasterCreateDao masterCreateDAO = new MasterCreateDao();
            masterCreateDAO.setGroupId(group.getGroupId());
            masterCreateDAO.setUserId(user.getUserId());
            group.setMaster(masterService.createMaster(masterCreateDAO));
            groupRepository.save(group);
        }
        return group;
    }

    public Page<GroupListDto> getGroups(Pageable pageable) {
        Page<Group> groups = groupRepository.findAllByGroupIdNotOrderByCreatedAtDesc(1L, pageable);

        return groups.map(groupMapper::groupToGroupListDto);
    }

    public int synchronizeSize(Long moimId) {
        Group group = groupRepository.findById(moimId).get();
        List<UserGroup> userList = userGroupRepository.findAllByGroup(group);
        if (group.getCurrentSize() !=  userList.size()) {
            group.setCurrentSize(userList.size());
            groupRepository.save(group);
            return userList.size();
        }
        return 0;
    }
}

