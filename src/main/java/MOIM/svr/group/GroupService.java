package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.master.MasterCreateDAO;
import MOIM.svr.master.MasterService;
import MOIM.svr.user.User;
import MOIM.svr.user.UserRepository;
import MOIM.svr.utilities.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;
    private UtilMethods utilMethods;
    private MasterService masterService;
    private final UserRepository userRepository;

    public void createGroup(GroupPostDto groupPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupMapper.groupPostDtoToGroup(groupPostDto);
        groupRepository.save(group);

        MasterCreateDAO masterCreateDAO = new MasterCreateDAO();
        masterCreateDAO.setGroupId(group.getGroupId());
        masterCreateDAO.setUserId(user.getUserId());
        group.setMaster(masterService.createMaster(masterCreateDAO));
        groupRepository.save(group);
    }

}
