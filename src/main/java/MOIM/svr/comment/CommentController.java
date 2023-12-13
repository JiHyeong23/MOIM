package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentMyResponseDto;
import MOIM.svr.comment.commentDto.CommentPatchDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
import MOIM.svr.utils.PageResponseDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import MOIM.svr.utils.UtilMethods;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static MOIM.svr.utils.PageResponseDto.*;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;
    private UtilMethods utilMethods;

    //댓글 작성
    @PostMapping
    public ResponseEntity postComment(@RequestBody CommentPostDto commentPostDto, HttpServletRequest request) {
        Comment comment = commentService.saveComment(commentPostDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                comment.getCreatedAt(), HttpStatus.CREATED, "Comment created successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 조회(내가 쓴 댓글)
    @GetMapping
    public ResponseEntity getComment(@RequestParam(value = "pageNo") int pageNo, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(pageNo, 10);
        Page<CommentMyResponseDto> comments = commentService.getComments(request, pageable);
        List<CommentMyResponseDto> content = comments.getContent();

        PageResponseDto response = utilMethods.makeSuccessPageResponseDto(
                content, "Comments got successfully", pageNo, comments);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 수정
    @PatchMapping
    public ResponseEntity patchComment(@RequestBody CommentPatchDto commentPatchDto, HttpServletRequest request) {
        Comment comment = commentService.modifyComment(commentPatchDto, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                comment.getCommentId(), "Comment modified successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        commentService.removeComment(commentId, request);

        ResponseDto response = utilMethods.makeSuccessResponseDto(
                commentId, HttpStatus.NO_CONTENT, "Comment deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
