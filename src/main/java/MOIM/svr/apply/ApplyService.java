package MOIM.svr.apply;

import MOIM.svr.apply.applyDto.ApplyPostDto;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import MOIM.svr.utilities.UtilMethods;
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
    public void supportApply(ApplyPostDto applyPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Apply apply = applyMapper.applyPostDtoToApply(applyPostDto);
        apply.setUser(user);
        applyRepository.save(apply);
    }

}
