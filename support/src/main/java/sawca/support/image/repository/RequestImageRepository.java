package sawca.support.image.repository;

import sawca.support.image.entity.RequestImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestImageRepository extends JpaRepository<RequestImage, Long> {
}
