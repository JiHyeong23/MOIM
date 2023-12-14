package MOIM.svr.utils;

import MOIM.svr.comment.CommentRepository;
import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.post.Post;
import MOIM.svr.post.PostRepository;
import MOIM.svr.security.JwtHelper;
import MOIM.svr.user.User;
import MOIM.svr.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static MOIM.svr.utils.PageResponseDto.*;

@Component
@AllArgsConstructor
public class UtilMethods {
    private UserRepository userRepository;
    private JwtHelper jwtHelper;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private final GroupRepository groupRepository;

    public User parseTokenForUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            String email = jwtHelper.getEmailFromJwtToken(token);
            return userRepository.findByEmail(email);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "토큰 확인 부탁드립니다 ^ㅡ^");
        }
    }

    public Post findPost(Long postId) {
        return postRepository.findById(postId).get();
    }

    public List<CommentResponseDto> getComments(Post post) {
        return commentRepository.findCommentResponseDtoByPost(post);
    }

    public Group findGroup(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

//    public <T> PageResponseDto.PageInfo makePageInfo(int pageNo, Page<T> response) {
//        PageResponseDto.PageInfo pageInfo = PageResponseDto.PageInfo.builder()
//                .pageNo(pageNo).pageSize(10).totalElements(response.getTotalElements())
//                .totalPages(response.getTotalPages()).last(response.isLast()).build();
//        return pageInfo;
//    }

    public <T, U> PageResponseDto makeSuccessPageResponseDto(T content, String memo, int pageNo, Page<U> pageResponse) {
        PageInfo pageInfo = PageInfo.builder()
                .pageNo(pageNo).pageSize(10).totalElements(pageResponse.getTotalElements())
                .totalPages(pageResponse.getTotalPages()).last(pageResponse.isLast()).build();

        PageResponseDto response = PageResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo(memo)
                .response(content).pageInfo(pageInfo).build();
        return response;
    }

    public <T> ResponseDto makeSuccessResponseDto(T content, String memo) {
        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo(memo)
                .response(content).build();
        return response;
    }

    public <T> ResponseDto makeSuccessResponseDto(T content, HttpStatus status, String memo) {
        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(status).memo(memo)
                .response(content).build();
        return response;
    }

    public <T> ResponseDto makeFailResponseDto(T content, String memo) {
        ResponseDto response = ResponseDto.builder()
                .result(Result.FAIL).httpStatus(HttpStatus.IM_USED).memo(memo)
                .response(content).build();
        return response;
    }

    public <T> ResponseDto makeResponseDto(Result result, HttpStatus status, T content, String memo) {
        ResponseDto response = ResponseDto.builder()
                .result(result).httpStatus(status).memo(memo)
                .response(content).build();
        return response;
    }
}
