package MOIM.svr.group.groupDto;

import lombok.Getter;

@Getter
public class GroupPostDto {
    private String groupName;
    private int maxSize;
    private String intro;
    private String groupImage;
    private String groupCategory;
}
