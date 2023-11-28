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
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@AllArgsConstructor
public class UtilMethods {
    private UserRepository userRepository;
    private JwtHelper jwtHelper;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private final GroupRepository groupRepository;

    public User parseTokenForUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtHelper.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
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
}
