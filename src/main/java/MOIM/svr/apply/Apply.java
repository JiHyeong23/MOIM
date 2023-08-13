package MOIM.svr.apply;

import javax.persistence.*;

@Entity
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long applyId;
    private Long groupId;
    private Long userId;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private Boolean handled;
}
