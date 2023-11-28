package MOIM.svr.user;

import MOIM.svr.UserGroup.UserGroup;
import MOIM.svr.comment.Comment;
import MOIM.svr.group.Group;
import MOIM.svr.master.Master;
import MOIM.svr.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@Getter
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userNickname;
    private String email;
    private String pw;
    @Column(columnDefinition = "DATE")
    private LocalDateTime dob;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    @Builder.Default
    private String intro = "";
    @Builder.Default
    private String userImage = "";
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserGroup> groups = new ArrayList<>();
    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Master> masters = new ArrayList<>();

    public User() {}

    public void setPw(String pw) {
        this.pw = pw;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User updateIntro(String newIntro) {
        this.intro = newIntro;
        return this;
    }

    public User updateNickname(String newNickname) {
        this.userNickname = newNickname;
        return this;
    }

    public User updateUserImage(String newUserImage) {
        this.userImage = newUserImage;
        return this;
    }
}
