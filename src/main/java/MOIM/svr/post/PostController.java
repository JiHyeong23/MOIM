package MOIM.svr.post;

import MOIM.svr.post.enums.PostCategory;
import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.post.postDto.PostDetailDto;
import MOIM.svr.post.postDto.PostListDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        postService.savePost(postCreationDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
    }

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        PostDetailDto postDetail = postService.getPostDetail(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postDetail);
    }

    //특정 유저 게시글(내가쓴글) 조회
    @GetMapping("/users")
    public ResponseEntity getPosts(HttpServletRequest request, Pageable pageable) {
        Page<PostListDto> posts = postService.getUserPosts(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    //카테고리별 조회
    @GetMapping("/ctg/{groupId}/{category}")
    public ResponseEntity getAllPosts(@PathVariable String category, @PathVariable Long groupId, Pageable pageable) {
        PostCategory postCategory;
        try {
            postCategory = PostCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid category value");
        }

        Page<PostListDto> posts = postService.getCategoryPosts(postCategory, groupId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
}
