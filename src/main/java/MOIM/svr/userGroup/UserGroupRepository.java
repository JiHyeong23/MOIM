package MOIM.svr.userGroup;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    Page<UserGroup> findByUser(User user, Pageable pageable);

    UserGroup findByUserAndGroup(User user, Group group);
    List<UserGroup> findAllByGroup(Group group);
}
