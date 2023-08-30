package MOIM.svr.post;

import MOIM.svr.comment.Comment;
import MOIM.svr.group.Group;
import MOIM.svr.utils.Category;
import MOIM.svr.post.enums.Status;
import MOIM.svr.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static MOIM.svr.post.enums.Status.POSTED;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;//db데이터타입변경
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = POSTED;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Builder.Default
    private Boolean notice = Boolean.FALSE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Post() {}

    public Post setUser(User user) {
        this.user = user;
        return this;
    }
}
