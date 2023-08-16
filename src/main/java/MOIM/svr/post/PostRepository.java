package MOIM.svr.post;

import MOIM.svr.post.enums.PostCategory;
import MOIM.svr.post.postDto.PostListDto;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findTop3ByUserOrderByCreatedAtDesc(User user);
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<Post> findByCategoryAndGroupIdOrderByCreatedAtDesc(PostCategory category, Long groupId, Pageable pageable);
}
