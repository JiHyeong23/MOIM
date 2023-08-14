package MOIM.svr.user;

import MOIM.svr.post.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@Builder
@Getter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userNickname;
    private String email;
    private String pw;
    private String DOB;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(columnDefinition = "TEXT")
    @Builder.Default
    private String intro = "";
    @Builder.Default
    private String userImage = "";
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


    public User() {}

    public void setPw(String pw) {
        this.pw = pw;
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
