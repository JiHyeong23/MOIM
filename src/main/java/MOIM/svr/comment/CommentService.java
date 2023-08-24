package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentMyResponseDto;
import MOIM.svr.comment.commentDto.CommentPatchDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
import MOIM.svr.post.Post;
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
public class CommentService {
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UtilMethods utilMethods;

    public Comment saveComment(CommentPostDto commentPostDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Post post = utilMethods.findPost(commentPostDto.getPostId());
        Comment comment = Comment.builder()
                .user(user)
                .body(commentPostDto.getBody())
                .post(post)
                .build();
        commentRepository.save(comment);
        return comment;
    }

    public Page<CommentMyResponseDto> getComments(HttpServletRequest request, Pageable pageable) {
        User user = utilMethods.parseTokenForUser(request);
        Page<Comment> comments = commentRepository.findByUser(user, pageable);
        return comments.map(comment -> {
            CommentMyResponseDto myCommentDto = commentMapper.commentToCommentMyResponseDto(comment);
            myCommentDto.setPostTitle(comment.getPost().getTitle());
            myCommentDto.setPostUserNickname(comment.getPost().getUser().getUserNickname());
            myCommentDto.setPostCreatedAt(comment.getPost().getCreatedAt());
            myCommentDto.setPostCommentCount((utilMethods.getComments(comment.getPost())).size());
            return myCommentDto;
        });
    }

    public Comment modifyComment(CommentPatchDto commentPatchDto, HttpServletRequest request) {
        Comment comment = commentRepository.findById(commentPatchDto.getCommentId()).get();
        if(commentPatchDto.getBody() != null) {
            comment.setBody(commentPatchDto.getBody());
            commentRepository.save(comment);
        }
        return comment;
    }

    public void removeComment(Long commentId, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        Comment comment = commentRepository.findById(commentId).get();
        if(comment.getUser() == user) {
            commentRepository.delete(comment);
        }
    }

}
