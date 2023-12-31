package MOIM.svr.apply;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    Page<Apply> findByHandledFalseAndGroup(Group group, Pageable pageable);
    Page<Apply> findByUser(User user, Pageable pageable);
}
