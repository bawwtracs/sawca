package sawca.support.im.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.im.entity.ChatGroup;

public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}
