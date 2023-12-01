package MOIM.svr.category;

import MOIM.svr.utils.ResponseDto;
import MOIM.svr.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/categorys")
public class CategoryController {
    private GroupCategoryRepository groupCategoryRepository;
    private PostCategoryRepository postCategoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryController(GroupCategoryRepository groupCategoryRepository, PostCategoryRepository postCategoryRepository,
                              CategoryMapper categoryMapper) {
        this.groupCategoryRepository = groupCategoryRepository;
        this.postCategoryRepository = postCategoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/group")
    public ResponseEntity getGroupCategory() {
        List<GroupCategory> categories = groupCategoryRepository.findAll();

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Categories got successfully")
                .response(categories).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/post")
    public ResponseEntity getPostCategory() {
        List<PostCategory> categories = postCategoryRepository.findAll();
        Stream<CategoryResponseDto> responseDto = categories.stream().map(categoryMapper::categoryToDto);

        ResponseDto response = ResponseDto.builder()
                .result(Result.SUCCESS).httpStatus(HttpStatus.OK).memo("Categories got successfully")
                .response(responseDto).build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
