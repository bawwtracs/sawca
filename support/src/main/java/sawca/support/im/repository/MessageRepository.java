package sawca.support.im.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.im.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
