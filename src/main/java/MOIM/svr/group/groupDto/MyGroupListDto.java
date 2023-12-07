package MOIM.svr.group.groupDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MyGroupListDto {
    private Long groupId;
    private String groupName;
    private int maxSize;
    private int currentSize;
    private int score;
    private String post;
    private String firstMeetTime;
}
