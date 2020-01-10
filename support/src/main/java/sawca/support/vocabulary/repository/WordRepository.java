package sawca.support.vocabulary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sawca.support.vocabulary.entity.Word;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {

    List<Word> findAllByOriginId(Long originId);

}
