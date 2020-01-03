package sawca.support.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.system.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
