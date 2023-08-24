package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentMyResponseDto;
import MOIM.svr.comment.commentDto.CommentPatchDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    //댓글 작성
    @PostMapping
    public ResponseEntity postComment(@RequestBody CommentPostDto commentPostDto, HttpServletRequest request) {
        Comment comment = commentService.saveComment(commentPostDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.CREATED).memo("Comment created successfully")
                .response(comment.getCreatedAt()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 조회(내가 쓴 댓글)
    @GetMapping
    public ResponseEntity getComment(HttpServletRequest request, Pageable pageable) {
        Page<CommentMyResponseDto> comments = commentService.getComments(request, pageable);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Comments got successfully")
                .response(comments).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 수정
    @PatchMapping
    public ResponseEntity patchComment(@RequestBody CommentPatchDto commentPatchDto, HttpServletRequest request) {
        Comment comment = commentService.modifyComment(commentPatchDto, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Comment modified successfully")
                .response(comment.getCommentId()).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId, HttpServletRequest request) {
        commentService.removeComment(commentId, request);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.NO_CONTENT).memo("Comment deleted successfully")
                .response(commentId).build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
