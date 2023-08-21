package MOIM.svr.UserGroup;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userGroupId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserGroup(Group group, User user) {
        this.group = group;
        this.user = user;
    }
}
