package MOIM.svr.group;

import MOIM.svr.post.Post;
import MOIM.svr.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_table")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;
    private String groupName;
    private Long masterId;
    private int maxSize;
    @Column(columnDefinition = "TEXT")
    private String intro;
    private String groupImage;
    private int score;
    @Enumerated(EnumType.STRING)
    private GroupCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
}
