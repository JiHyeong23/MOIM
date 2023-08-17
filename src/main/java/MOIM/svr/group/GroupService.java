package MOIM.svr.group;

import MOIM.svr.group.groupDto.GroupPostDto;
import MOIM.svr.user.User;
import MOIM.svr.utilities.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class GroupService {
    private GroupRepository groupRepository;
    private GroupMapper groupMapper;
    private UtilMethods utilMethods;

    public void createGroup(GroupPostDto groupPostDto, HttpServletRequest request) {
        Group group = groupMapper.groupPostDtoToGroup(groupPostDto);
        User user = utilMethods.parseTokenForUser(request);
        group.setMasterId(user.getUserId());

    }
}
