package MOIM.svr.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Master {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long masterId;
    //
    private Long groupId;
    private Long userId;

}
