package MOIM.svr.group;

import javax.persistence.*;

@Entity
@Table(name = "group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long groupId;
    private int max_size;
    @Column(columnDefinition = "TEXT")
    private String intro;
    private String groupImage;
    private int score;
    @Enumerated(EnumType.STRING)
    private GroupCategory category;
    private String masterId;


}
