package sawca.support.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.vocabulary.entity.Word;

public interface WordRepository extends JpaRepository<Word, Long> {
}
