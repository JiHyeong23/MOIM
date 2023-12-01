package MOIM.svr.category;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
public class PostCategory {
    @Id
    private Long categoryId;
    private String nameEn;
    private String nameKr;
}
