package MOIM.svr.group;

import MOIM.svr.UserGroup.UserGroup;
import MOIM.svr.apply.Apply;
import MOIM.svr.master.Master;
import MOIM.svr.post.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_table")
@Getter
@Setter
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String groupName;
    @OneToOne(mappedBy = "group")
    private Master master;
    private int maxSize;
    private int currentSize;
    private LocalDateTime createdAt;
    @Column(columnDefinition = "TEXT")
    private String intro;
    private String groupImage;
    private int score;
    private String groupCategory;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroup> users = new ArrayList<>();
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apply> applies = new ArrayList<>();
}
