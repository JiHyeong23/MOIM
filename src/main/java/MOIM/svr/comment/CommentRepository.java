package MOIM.svr.comment;

import MOIM.svr.comment.commentDto.CommentResponseDto;
import MOIM.svr.post.Post;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByUser(User user, Pageable pageable);

    @Query("SELECT NEW MOIM.svr.comment.commentDto.CommentResponseDto(c.body, c.createdAt, c.user.userId, u.userNickname) FROM Comment c JOIN User u ON c.user.userId = u.userId WHERE c.post = :post")
    List<CommentResponseDto> findCommentResponseDtoByPost(@Param("post") Post post);
}
