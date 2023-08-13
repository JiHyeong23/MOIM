package MOIM.svr.report;

import javax.persistence.*;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;
    private Long userId;
    private Long postId;
    private Long commentId;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private Boolean handled;
}
