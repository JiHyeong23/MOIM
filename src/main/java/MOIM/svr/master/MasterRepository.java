package MOIM.svr.master;

import MOIM.svr.group.Group;
import MOIM.svr.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Master findByUserAndGroup(User user, Group group);
}
