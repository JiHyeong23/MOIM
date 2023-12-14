package MOIM.svr.group.groupDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class GroupPostDto {
    @NotBlank
    @Size(min = 2, message = "모임이름은 2글자 이상이어야 합니다.")
    private String groupName;
    @NotNull
    @Min(value = 10, message = "모임 인원은 최소 10명입니다.")
    private int maxSize;
    @NotBlank(message = "모임소개를 적어주세요.")
    private String intro;
    private String groupImage;
    @NotBlank(message = "모임 카테고리를 선택해주세요.")
    private String groupCategory;
}
