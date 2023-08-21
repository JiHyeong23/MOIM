package MOIM.svr.master;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long masterId;
    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
