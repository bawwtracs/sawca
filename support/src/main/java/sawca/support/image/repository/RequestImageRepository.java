package sawca.support.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.image.entity.RequestImage;

public interface RequestImageRepository extends JpaRepository<RequestImage, Long> {
}
