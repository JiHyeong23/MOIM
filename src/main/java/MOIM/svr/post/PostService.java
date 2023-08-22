package MOIM.svr.post;

import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.group.Group;
import MOIM.svr.utils.Category;
import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.post.postDto.PostDetailDto;
import MOIM.svr.post.postDto.PostListDto;
import MOIM.svr.user.User;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private PostMapper postMapper;
    private PostRepository postRepository;
    private UtilMethods utilMethods;

    public Post savePost(PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postMapper.postCreationDtoToPost(postCreationDto);
        User user = utilMethods.parseTokenForUser(request);
        post.setUser(user);

        postRepository.save(post);
        return post;
    }

    public PostDetailDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId).get();
        PostDetailDto postDetailDto = postMapper.postToPostDetailDto(post);

        List<CommentResponseDto> comments = utilMethods.getComments(post);
        postDetailDto.setComments(comments);
        postDetailDto.setCommentsCount((long) comments.size());

        return postDetailDto;
    }

    public Page<PostListDto> getUserPosts(HttpServletRequest request, Pageable pageable) {
        User user = utilMethods.parseTokenForUser(request);
        Page<Post> posts = postRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        return posts.map(post -> {
            PostListDto postListDto = postMapper.postToPostListDto(post);
            postListDto.setCommentsCount((long) post.getComments().size());
            return postListDto;
        });
    }

    public Page<PostListDto> getCategoryPosts(Category category, Long groupId, Pageable pageable) {
        Group group = utilMethods.findGroup(groupId);
        Page<Post> posts = postRepository.findByCategoryAndGroupOrderByCreatedAtDesc(category, group, pageable);

        return posts.map(post -> {
            PostListDto postListDto = postMapper.postToPostListDto(post);
            postListDto.setCommentsCount((long) post.getComments().size());
            return postListDto;
        });
    }
}
