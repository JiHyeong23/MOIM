package MOIM.svr.master;

import MOIM.svr.exception.CustomException;
import MOIM.svr.exception.ErrorCode;
import MOIM.svr.userGroup.UserGroupService;
import MOIM.svr.apply.Apply;
import MOIM.svr.apply.ApplyMapper;
import MOIM.svr.apply.ApplyRepository;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.group.groupDto.GroupPatchDto;
import MOIM.svr.post.Post;
import MOIM.svr.post.PostRepository;
import MOIM.svr.schedule.*;
import MOIM.svr.schedule.ScheduleDto.SchedulePatchDto;
import MOIM.svr.schedule.ScheduleDto.SchedulePostDto;
import MOIM.svr.user.User;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class MasterService {
    private UtilMethods utilMethods;
    private final MasterRepository masterRepository;
    private ApplyRepository applyRepository;
    private ApplyMapper applyMapper;
    private MasterMapper masterMapper;
    private UserGroupService userGroupService;
    private final GroupRepository groupRepository;
    private ScheduleMapper scheduleMapper;
    private ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    private final PostRepository postRepository;

    //마스터 설정
    public Master createMaster(MasterCreateDao masterCreateDao) {
        Master master = masterMapper.toMaster(masterCreateDao);
        masterRepository.save(master);
        return master;
    }

    public Master certifyMaster(Long groupId, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupRepository.findById(groupId).get();
        if (user != group.getMaster().getUser()) {
            throw new CustomException(ErrorCode.NOT_MASTER);
        }
        return masterRepository.findByUserAndGroup(user, group);
    }

    public Page<ApplyDetailDto> getApplies(Long groupId, Pageable pageable, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Group group = utilMethods.findGroup(groupId);
        Page<Apply> applies = applyRepository.findByHandledFalseAndGroup(group, pageable);

        return applies.map(applyMapper::applyToApplyDetailDto);
    }

    public ApplyDetailDto getApplyDetail(Long groupId, Long applyId, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Apply apply = applyRepository.findById(applyId).get();

        return applyMapper.applyToApplyDetailDto(apply);
    }

    public Apply acceptMember(Long groupId, Long applyId, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Apply apply = applyRepository.findById(applyId).get();
        Group group = groupRepository.findById(groupId).get();
        if(group.getMaxSize() > group.getUsers().size()) {
            apply.setHandled(Boolean.TRUE);
            applyRepository.save(apply);
            userGroupService.createUserGroup(groupId, apply.getUser().getUserId());
            group.setCurrentSize(group.getCurrentSize()+1);
            groupRepository.save(group);
        } else {
            throw new CustomException(ErrorCode.IS_FULL);
        }
        return apply;
    }

    public Apply rejectMember(Long groupId, Long applyId, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Apply apply = applyRepository.findById(applyId).get();
        apply.setHandled(Boolean.TRUE);
        applyRepository.save(apply);

        return apply;
    }

    public Schedule createSchedule(Long groupId, SchedulePostDto schedulePostDto, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(schedulePostDto);
        schedule.setGroupId(groupId);
        scheduleRepository.save(schedule);

        return schedule;
    }

    public Schedule modifySchedule(Long groupId, SchedulePatchDto schedulePatchDto, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Schedule schedule = scheduleRepository.findById(schedulePatchDto.getScheduleId()).get();
        if(schedulePatchDto.getScheduleName() != null) {
            schedule.setScheduleName(schedulePatchDto.getScheduleName());
        }
        if(schedulePatchDto.getStartDate() != null) {
                schedule.setStartDate(schedulePatchDto.getStartDate());
            }
        if(schedulePatchDto.getEndDate() != null) {
            schedule.setEndDate(schedulePatchDto.getEndDate());
        }
        if(schedulePatchDto.getHightlight() != null) {
            schedule.setHighlight(schedulePatchDto.getHightlight());
        }
        scheduleRepository.save(schedule);

        return schedule;
    }

    public void deleteSchedule(Long groupId, Long scheduleId, HttpServletRequest request) {
        certifyMaster(groupId, request);
        scheduleRepository.deleteById(scheduleId);
    }

    public void postSetNotice(Long groupId, Long postId, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Post post = postRepository.findById(postId).get();
        post.setNotice(Boolean.TRUE);
        postRepository.save(post);
    }

    public Group modifyGroupInfo(Long groupId, GroupPatchDto groupPatchDto, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Group group = groupRepository.findById(groupId).get();
        if(groupPatchDto.getIntro() != null) {
            group.setIntro(groupPatchDto.getIntro());
        }
        if(groupPatchDto.getGroupImage() != null) {
            group.setGroupImage(groupPatchDto.getGroupImage());
        }
        groupRepository.save(group);

        return group;
    }

    public Group modifyGroupNumber(Long groupId, Long modifyNumber, HttpServletRequest request) {
        certifyMaster(groupId, request);
        Group group = groupRepository.findById(groupId).get();
        if (group.getMaxSize() > modifyNumber) {
            if (group.getCurrentSize() < modifyNumber) {
                group.setMaxSize(Math.toIntExact(modifyNumber));
            }
            else {
                throw new CustomException(ErrorCode.HAVE_MORE);
            }
        } else if (group.getMaxSize() < modifyNumber) {
            group.setMaxSize(Math.toIntExact(modifyNumber));
        } else {
            throw new CustomException(ErrorCode.SAME_SIZE);
        }
        return group;
    }

    public void deleteGroup(Long groupId, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupRepository.findById(groupId).get();
        if(group.getCurrentSize() == 0) {
            masterRepository.delete(masterRepository.findByUserAndGroup(user, group));
            groupRepository.delete(group);
        } else {
            throw new CustomException(ErrorCode.HAVE_USER);
        }
    }
}
