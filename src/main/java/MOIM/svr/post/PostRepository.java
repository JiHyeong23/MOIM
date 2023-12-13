package MOIM.svr.post;

import MOIM.svr.group.Group;
import MOIM.svr.post.postDto.MyPostListDto;
import MOIM.svr.utils.Category;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT NEW MOIM.svr.post.postDto.MyPostListDto (p.user.userId, p.postId, p.title, p.createdAt, " +
            "(SELECT COUNT(c) FROM Comment c WHERE c.post.postId = p.postId)) " +
            "FROM Post p WHERE p.user.userId = :userId ORDER BY p.createdAt DESC")
    List<MyPostListDto> findMyPostListDto(Long userId);
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    Page<Post> findByCategoryAndGroupOrderByCreatedAtDesc(Category category, Group group, Pageable pageable);
    Post findFirstByGroupOrderByCreatedAtDesc(Group group);
}
