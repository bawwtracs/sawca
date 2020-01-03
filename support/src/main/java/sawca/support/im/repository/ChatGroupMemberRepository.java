package sawca.support.im.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.im.entity.ChatGroupMember;

public interface ChatGroupMemberRepository extends JpaRepository<ChatGroupMember, Long> {
}
