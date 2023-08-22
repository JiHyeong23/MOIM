package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.user.User;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class ApplyService {
    private ApplyRepository applyRepository;
    private GroupRepository groupRepository;
    private ApplyMapper applyMapper;
    private UtilMethods utilMethods;

    public Apply askApply(ApplyPostDto applyPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Apply apply = applyMapper.applyPostDtoToApply(applyPostDto);
        apply.setUser(user);
        applyRepository.save(apply);
        return apply;
    }

}
