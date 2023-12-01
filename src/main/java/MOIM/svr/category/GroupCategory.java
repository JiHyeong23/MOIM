package MOIM.svr.category;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

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
