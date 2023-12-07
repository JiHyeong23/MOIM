package MOIM.svr.post;

import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.group.Group;
import MOIM.svr.group.GroupRepository;
import MOIM.svr.post.postDto.PostPatchDto;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private PostMapper postMapper;
    private PostRepository postRepository;
    private UtilMethods utilMethods;
    private final GroupRepository groupRepository;

    public Post savePost(PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postMapper.postCreationDtoToPost(postCreationDto);
        User user = utilMethods.parseTokenForUser(request);
        post.setUser(user);
        postRepository.save(post);

        Group group = groupRepository.findById(postCreationDto.getGroupId()).get();
        group.setScore(group.getScore()+1);
        groupRepository.save(group);
        return post;
    }

    public Post patchPost(PostPatchDto postPatchDto, HttpServletRequest request) {
        Post post = postRepository.findById(postPatchDto.getPostId()).get();
        User user = utilMethods.parseTokenForUser(request);
        if(user == post.getUser()) {
            if (postPatchDto.getTitle() != null) {
                post.setTitle(postPatchDto.getTitle());
            }
            if (postPatchDto.getBody() != null) {
                post.setBody(postPatchDto.getBody());
            }
            if (postPatchDto.getCategory() != null) {
                post.setCategory(postPatchDto.getCategory());
            }
            postRepository.save(post);
            return post;
        }
        else {
            return null;
        }
    }

    public void deletePost(Long postId, HttpServletRequest request) {
        Post post = postRepository.findById(postId).get();
        User user = utilMethods.parseTokenForUser(request);
        if(user == post.getUser()) {
            postRepository.deleteById(postId);
        }
    }

    public PostDetailDto getPostDetail(HttpServletRequest request, Long postId) {
        User user = utilMethods.parseTokenForUser(request);
        Post post = postRepository.findById(postId).get();
        PostDetailDto postDetailDto = postMapper.postToPostDetailDto(post);
        postDetailDto.setUserId(user.getUserId());
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
