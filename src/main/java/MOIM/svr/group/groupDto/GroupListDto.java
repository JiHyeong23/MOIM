package MOIM.svr.group.groupDto;

import MOIM.svr.group.GroupCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupListDto {
    private Long groupId;
    private String groupName;
    private int maxSize;
    private int currentSize;
    private String groupImage;
    private GroupCategory groupCategory;
}
