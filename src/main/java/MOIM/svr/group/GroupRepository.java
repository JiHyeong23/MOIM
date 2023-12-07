package MOIM.svr.group;

import MOIM.svr.master.Master;
import MOIM.svr.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Page<Group> findAllByGroupIdNotOrderByCreatedAtDesc(Long groupId, Pageable pageable);
}
