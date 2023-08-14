package MOIM.svr.comment;

import MOIM.svr.post.Post;
import MOIM.svr.post.enums.Status;
import MOIM.svr.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private Long userId;
    private String body;
    private LocalDateTime createdAt;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
