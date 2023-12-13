package MOIM.svr.category;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class GroupCategory {
    @Id
    private Long categoryId;
    private String nameEn;
    private String nameKr;
}
