package MOIM.svr.master;

import MOIM.svr.UserGroup.UserGroupService;
import MOIM.svr.apply.Apply;
import MOIM.svr.apply.ApplyMapper;
import MOIM.svr.apply.ApplyRepository;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MasterService {
    private UtilMethods utilMethods;
    private final MasterRepository masterRepository;
    private ApplyRepository applyRepository;
    private ApplyMapper applyMapper;
    private MasterMapper masterMapper;
    private UserGroupService userGroupService;

    public Master createMaster(MasterCreateDAO masterCreateDAO) {
        Master master = masterMapper.toMaster(masterCreateDAO);
        masterRepository.save(master);
        return master;
    }

    public Page<ApplyDetailDto> getApplies(Long groupId, Pageable pageable) {
        Group group = utilMethods.findGroup(groupId);
        Page<Apply> applies = applyRepository.findByGroup(group, pageable);
        return applies.map(applyMapper::applyToApplyDetailDto);
    }

    public ApplyDetailDto getApplyDetail(Long applyId) {
        Apply apply = applyRepository.findById(applyId).get();
        return applyMapper.applyToApplyDetailDto(apply);
    }

    public Apply acceptMember(Long groupId, Long applyId) {
        Apply apply = applyRepository.findById(applyId).get();
        apply.setHandled(Boolean.TRUE);
        applyRepository.save(apply);
        userGroupService.createUserGroup(groupId, apply.getUser().getUserId());
        return apply;
    }
}
