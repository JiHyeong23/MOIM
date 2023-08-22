package MOIM.svr.apply;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime handledAt;
    private Boolean handled = Boolean.FALSE;

}
