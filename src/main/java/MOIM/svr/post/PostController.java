package MOIM.svr.post;

import MOIM.svr.post.postDto.PostCreationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        postService.createPost(postCreationDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

//    @GetMapping
//    public ResponseEntity getPost(@RequestBody Long postId) {
//        postService.get
//    }
}
