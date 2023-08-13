package MOIM.svr.comment;

import MOIM.svr.post.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private Long postId;
    private Long userId;
    private String body;
    private LocalDateTime createdAt;
    private Status status;
}
