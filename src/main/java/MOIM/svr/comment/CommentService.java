package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentGetDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.post.Post;
import MOIM.svr.user.User;
import MOIM.svr.utilities.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UtilMethods utilMethods;

    public void saveComment(CommentPostDto commentPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Post post = utilMethods.findPost(commentPostDto.getPostId());
        Comment comment = Comment.builder()
                .user(user)
                .body(commentPostDto.getBody())
                .post(post)
                .build();
        commentRepository.save(comment);
    }

    public Page<CommentResponseDto> getComments(CommentGetDto commentGetDto, Pageable pageable) {
        return commentRepository.findCommentResponseDtoByPost(utilMethods.findPost(commentGetDto.getPostId()), pageable);
    }
}
