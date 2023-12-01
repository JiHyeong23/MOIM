package MOIM.svr.category;

import MOIM.svr.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "nameEn", target = "nameEn")
    @Mapping(source = "nameKr", target = "nameKr")
    CategoryResponseDto categoryToDto(PostCategory postCategory);
}
