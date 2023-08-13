package MOIM.svr.post;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    private String userId;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;
}
