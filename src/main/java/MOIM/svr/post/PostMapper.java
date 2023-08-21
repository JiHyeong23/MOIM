package MOIM.svr.post;

import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.post.postDto.PostDetailDto;
import MOIM.svr.post.postDto.PostListDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "groupId", target = "group.groupId")
    @Mapping(source = "category", target = "category")
    Post postCreationDtoToPost(PostCreationDto postCreationDto);

    @Mapping(source = "title", target = "title")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "group.groupId", target = "groupId")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "user.userNickname", target = "userNickname")
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "commentsCount", ignore = true)
    PostDetailDto postToPostDetailDto(Post post);

    @Mapping(source = "postId", target = "postId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "user.userNickname", target = "userNickname")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(target = "commentsCount", ignore = true)
    PostListDto postToPostListDto(Post post);
}
