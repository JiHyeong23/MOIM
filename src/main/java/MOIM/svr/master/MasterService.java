package MOIM.svr.master;

import MOIM.svr.UserGroup.UserGroupService;
import MOIM.svr.apply.Apply;
import MOIM.svr.apply.ApplyMapper;
import MOIM.svr.apply.ApplyRepository;
import MOIM.svr.apply.applyDto.ApplyDetailDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
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
import java.util.Optional;

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

    //마스터 인증 + 수정예정
    public Master certifyMaster(Long groupId, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupRepository.findById(groupId).get();
        return masterRepository.findByUserAndGroup(user, group);
    }

    public Page<ApplyDetailDto> getApplies(Long groupId, Pageable pageable, HttpServletRequest request) {
        if (certifyMaster(groupId, request) != null) {
            Group group = utilMethods.findGroup(groupId);
            Page<Apply> applies = applyRepository.findByGroup(group, pageable);
            return applies.map(applyMapper::applyToApplyDetailDto);
        }
        else {
            return null; //임시
        }

    }

    public ApplyDetailDto getApplyDetail(Long groupId, Long applyId, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
            Apply apply = applyRepository.findById(applyId).get();
            return applyMapper.applyToApplyDetailDto(apply);
        }
        else {
            return null; //임시
        }
    }

    public Apply acceptMember(Long groupId, Long applyId, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
            Apply apply = applyRepository.findById(applyId).get();
            apply.setHandled(Boolean.TRUE);
            applyRepository.save(apply);
            userGroupService.createUserGroup(groupId, apply.getUser().getUserId());
            return apply;
        }
        else {
            return null; //임시
        }
    }

    public Schedule createSchedule(Long groupId, SchedulePostDto schedulePostDto, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
            Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(schedulePostDto);
            schedule.setGroupId(groupId);
            scheduleRepository.save(schedule);
            return schedule;
        }
        else {
            return null;
        }
    }

    public Schedule modifySchedule(Long groupId, SchedulePatchDto schedulePatchDto, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
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
        } else {
            return null;
        }
    }

    public void deleteSchedule(Long groupId, Long scheduleId, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
            scheduleRepository.deleteById(scheduleId);
        }
    }

    public void postSetNotice(Long groupId, Long postId, HttpServletRequest request) {
        if(certifyMaster(groupId, request) != null) {
            Post post = postRepository.findById(postId).get();
            post.setNotice(Boolean.TRUE);
            postRepository.save(post);
        }
    }
}
