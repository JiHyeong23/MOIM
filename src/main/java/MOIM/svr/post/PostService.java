package MOIM.svr.post;

import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.user.User;
import MOIM.svr.utilities.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private PostMapper postMapper;
    private PostRepository postRepository;
    private UtilMethods utilMethods;

    public void createPost(PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postMapper.postCreationDtoToPost(postCreationDto);
        User user = utilMethods.parseTokenForUser(request);
        post.setUser(user);

        postRepository.save(post);
    }

//    public Post getPostDetail(Long postId) {
//        Post post = postRepository.findById(postId).get();
//    }
}
