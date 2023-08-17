package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentMyResponseDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
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
    public ResponseEntity<String> postComment(@RequestBody CommentPostDto commentPostDto, HttpServletRequest request) {
        commentService.saveComment(commentPostDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    //댓글 조회(내가 쓴 댓글)
    @GetMapping
    public ResponseEntity getComment(HttpServletRequest request, Pageable pageable) {
        Page<CommentMyResponseDto> comments = commentService.getComments(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }
}
