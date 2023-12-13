package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupListDto;
import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.master.MasterCreateDao;
import MOIM.svr.master.MasterService;
import MOIM.svr.user.User;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;
    private UtilMethods utilMethods;
    private MasterService masterService;

    public Group createGroup(GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupMapper.groupPostDtoToGroup(groupPostDto);
        group.setGroupCategory(groupPostDto.getGroupCategory());
        group.setCreatedAt(LocalDateTime.now());
        group.setCurrentSize(1);
        groupRepository.save(group);

        MasterCreateDao masterCreateDAO = new MasterCreateDao();
        masterCreateDAO.setGroupId(group.getGroupId());
        masterCreateDAO.setUserId(user.getUserId());
        group.setMaster(masterService.createMaster(masterCreateDAO));
        groupRepository.save(group);
        return group;
    }

    public Page<GroupListDto> getGroups(Pageable pageable) {
        Page<Group> groups = groupRepository.findAllByGroupIdNotOrderByCreatedAtDesc(1L, pageable);

        return groups.map(group -> {
            return groupMapper.groupToGroupListDto(group);
        });
    }

}
