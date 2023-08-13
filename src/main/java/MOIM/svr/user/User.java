package MOIM.svr.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_table")
@Builder
@Getter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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


    public User() {}

    public void setPw(String pw) {
        this.pw = pw;
    }

    public User updateIntro(String newIntro) {
        this.intro = newIntro;
        return this;
    }
}
