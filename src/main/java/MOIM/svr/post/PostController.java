package MOIM.svr.post;

import MOIM.svr.post.postDto.PostPatchDto;
import MOIM.svr.utils.Category;
import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.post.postDto.PostDetailDto;
import MOIM.svr.post.postDto.PostListDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
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
    public ResponseEntity createPost(@RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postService.savePost(postCreationDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Post created successfully")
                .response(post.getCreatedAt()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 수정
    @PatchMapping
    public ResponseEntity patchPost(@RequestBody PostPatchDto postPatchDto, HttpServletRequest request) {
        Post post = postService.patchPost(postPatchDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Post patched successfully")
                .response(post.getTitle()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.NO_CONTENT).memo("Post deleted successfully")
                .response(postId).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        PostDetailDto postDetail = postService.getPostDetail(postId);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Post got successfully")
                .response(postDetail).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //내가쓴글 조회
    @GetMapping("/my")
    public ResponseEntity getPosts(HttpServletRequest request, Pageable pageable) {
        Page<PostListDto> posts = postService.getUserPosts(request, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("My Posts got successfully")
                .response(posts).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //카테고리별 조회
    @GetMapping("/ctg/{groupId}/{category}")
    public ResponseEntity getAllPosts(@PathVariable String category, @PathVariable Long groupId, Pageable pageable) {
        Category postCategory;
        try {
            postCategory = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid category value");
        }
        Page<PostListDto> posts = postService.getCategoryPosts(postCategory, groupId, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Category posts got successfully")
                .response(posts).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
