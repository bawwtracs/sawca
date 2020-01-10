package sawca.support.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.image.entity.Request;

public interface RequestRepository extends JpaRepository<Request, Long> {
}
