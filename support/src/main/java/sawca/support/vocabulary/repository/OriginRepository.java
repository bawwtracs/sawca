package sawca.support.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.vocabulary.entity.Origin;

public interface OriginRepository extends JpaRepository<Origin, Long> {
}
