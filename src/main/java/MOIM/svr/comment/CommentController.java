package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentGetDto;
import MOIM.svr.comment.commentDto.CommentPostDto;
import MOIM.svr.comment.commentDto.CommentResponseDto;
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
    @PostMapping
    public ResponseEntity<String> postComment(@RequestBody CommentPostDto commentPostDto, HttpServletRequest request) {
        commentService.saveComment(commentPostDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
    }

    @GetMapping
    public ResponseEntity getComment(@RequestBody CommentGetDto commentGetDto, Pageable pageable) {
        Page<CommentResponseDto> comments = commentService.getComments(commentGetDto, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }
}
