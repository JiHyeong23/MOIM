package MOIM.svr.post;

import MOIM.svr.post.postDto.PostCreationDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PostMapper {
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "groupId", target = "groupId")
    @Mapping(source = "category", target = "category")
    Post postCreationDtoToPost(PostCreationDto postCreationDto);
}
