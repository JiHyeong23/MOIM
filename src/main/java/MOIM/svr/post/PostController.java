package MOIM.svr.post;

import MOIM.svr.post.postDto.PostPatchDto;
import MOIM.svr.utils.*;
import MOIM.svr.post.postDto.PostCreationDto;
import MOIM.svr.post.postDto.PostDetailDto;
import MOIM.svr.post.postDto.PostListDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;
    private UtilMethods utilMethods;

    //게시글 작성
    @PostMapping
    public ResponseEntity createPost(@RequestBody PostCreationDto postCreationDto, HttpServletRequest request) {
        Post post = postService.savePost(postCreationDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                post.getPostId(), HttpStatus.CREATED, "Post created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 수정
    @PatchMapping
    public ResponseEntity patchPost(@RequestBody PostPatchDto postPatchDto, HttpServletRequest request) {
        Post post = postService.patchPost(postPatchDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                post.getPostId(), "Post patched successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                postId, HttpStatus.NO_CONTENT, "Post deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //게시글 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity getPost(@PathVariable Long postId, HttpServletRequest request) {
        PostDetailDto postDetail = postService.getPostDetail(request, postId);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                postDetail, "Post got successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //내가쓴글 조회
    @GetMapping("/my")
    public ResponseEntity getPostList(@RequestParam(value = "pageNo") int pageNo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<PostListDto> posts = postService.getUserPosts(request, pageable);
        List<PostListDto> content = posts.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "My Posts got successfully", pageNo, posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //카테고리별 조회
    @GetMapping("/ctg/{groupId}/{category}")
    public ResponseEntity getAllPosts(@PathVariable String category, @PathVariable Long groupId,
                                      @RequestParam(value = "pageNo") int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Category postCategory;
        try {
            postCategory = Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid category value");
        }
        Page<PostListDto> posts = postService.getCategoryPosts(postCategory, groupId, pageable);
        List<PostListDto> content = posts.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "Category posts got successfully", pageNo, posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
