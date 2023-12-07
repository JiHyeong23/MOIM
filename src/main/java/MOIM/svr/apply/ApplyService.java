package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.apply.applyDto.MyApplyListDto;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.user.User;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class ApplyService {
    private ApplyRepository applyRepository;
    private ApplyMapper applyMapper;
    private UtilMethods utilMethods;

    public Apply askApply(ApplyPostDto applyPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Apply apply = applyMapper.applyPostDtoToApply(applyPostDto);
        apply.setUser(user);
        applyRepository.save(apply);
        return apply;
    }

    public Page<MyApplyListDto> findMyApply(HttpServletRequest request, Pageable pageable) {
        User user = utilMethods.parseTokenForUser(request);
        Page<Apply> applys = applyRepository.findByUser(user, pageable);

        return applys.map(apply -> {
            MyApplyListDto myListDto = applyMapper.applyToMyList(apply);
            myListDto.setGroupId(apply.getGroup().getGroupId());
            myListDto.setGroupName(apply.getGroup().getGroupName());
            myListDto.setMaster(apply.getGroup().getMaster().getUser().getUserNickname());
            return myListDto;
        });
    }
}
