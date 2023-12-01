package MOIM.svr.master;

import MOIM.svr.UserGroup.UserGroupService;
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
        System.out.println(certifyMaster(groupId, request) != null);
        if (certifyMaster(groupId, request) != null) {
            System.out.println("==================================");
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
        Apply apply = applyRepository.findById(applyId).get();
        if(certifyMaster(groupId, request) != null) {
            Group group = groupRepository.findById(groupId).get();
            if(group.getMaxSize() > group.getUsers().size()) {
                apply.setHandled(Boolean.TRUE);
                applyRepository.save(apply);
                userGroupService.createUserGroup(groupId, apply.getUser().getUserId());
                group.setCurrentSize(group.getCurrentSize()+1);
                groupRepository.save(group);
            }
        }
        return apply; //인원 full 시 자동거절 추가구현 필요
    }

    public Apply rejectMember(Long groupId, Long applyId, HttpServletRequest request) {
        Apply apply = applyRepository.findById(applyId).get();
        if(certifyMaster(groupId, request) != null) {
            apply.setHandled(Boolean.TRUE);
            applyRepository.save(apply);
        }
        return apply;
    }

    public Schedule createSchedule(Long groupId, SchedulePostDto schedulePostDto, HttpServletRequest request) {
        Schedule schedule = scheduleMapper.schedulePostDtoToSchedule(schedulePostDto);
        if(certifyMaster(groupId, request) != null) {
            schedule.setGroupId(groupId);
            scheduleRepository.save(schedule);
        }
        return schedule;
    }

    public Schedule modifySchedule(Long groupId, SchedulePatchDto schedulePatchDto, HttpServletRequest request) {
        Schedule schedule = scheduleRepository.findById(schedulePatchDto.getScheduleId()).get();
        if(certifyMaster(groupId, request) != null) {
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
        }
        return schedule;
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

    public Group modifyGroupInfo(Long groupId, GroupPatchDto groupPatchDto, HttpServletRequest request) {
        Group group = groupRepository.findById(groupId).get();
        if(certifyMaster(groupId, request) != null) {
            if(groupPatchDto.getIntro() != null) {
                group.setIntro(groupPatchDto.getIntro());
            }
            if(groupPatchDto.getGroupImage() != null) {
                group.setGroupImage(groupPatchDto.getGroupImage());
            }
            groupRepository.save(group);
        }
        return group;
    }

    public Group modifyGroupNumber(Long groupId, Long modifyNumber, HttpServletRequest request) {
        Group group = groupRepository.findById(groupId).get();
        if(certifyMaster(groupId, request) != null) {
            if (group.getMaxSize() > modifyNumber) {
                if (group.getUsers().size() < modifyNumber) {
                    group.setMaxSize(Math.toIntExact(modifyNumber));
                }
                else {
                    return null; //에러처리
                }
            }
            else if (group.getMaxSize() < modifyNumber) {
                group.setMaxSize(Math.toIntExact(modifyNumber));
            }
        }
        return group;
    }

    public void deleteGroup(Long groupId, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Group group = groupRepository.findById(groupId).get();
        if(group.getUsers().size() == 0) {
            masterRepository.delete(masterRepository.findByUserAndGroup(user, group));
            groupRepository.delete(group);
        }
    }
}
