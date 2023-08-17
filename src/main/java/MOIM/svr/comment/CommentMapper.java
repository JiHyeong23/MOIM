package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentMyResponseDto;
import MOIM.svr.comment.commentDto.CommentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CommentMapper {
    @Mapping(source = "body", target = "body")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(target = "postTitle", ignore = true)
    @Mapping(target = "postUserNickname", ignore = true)
    @Mapping(target = "postCommentCount", ignore = true)
    @Mapping(target = "postCreatedAt", ignore = true)
    CommentMyResponseDto commentToCommentMyResponseDto(Comment comment);
}
