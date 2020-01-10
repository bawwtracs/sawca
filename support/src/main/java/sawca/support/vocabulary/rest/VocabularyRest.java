package sawca.support.vocabulary.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import sawca.support.vocabulary.entity.Origin;
import sawca.support.vocabulary.entity.Word;
import sawca.support.vocabulary.repository.OriginRepository;
import sawca.support.vocabulary.repository.WordRepository;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyRest {

    @Resource
    private WordRepository wordRepository;
    @Resource
    private OriginRepository originRepository;

    @PostMapping("/word")
    public Word createWord(@RequestBody Word word) {
        return wordRepository.save(word);
    }

    @GetMapping("/words")
    public List<Word> getAllWords() {
        return wordRepository.findAll();
    }

    @GetMapping("/word/{id}")
    public Word getWordById(@PathVariable("id") Long id) {
        return wordRepository.getOne(id);
    }

    @PutMapping("/word/{id}")
    public Word updateWordById(@PathVariable("id") Long id, @RequestBody Word word) {
        word.setId(id);
        return wordRepository.save(word);
    }

    @DeleteMapping("/word/{id}")
    public Long deleteWordById(@PathVariable("id") Long id) {
        wordRepository.deleteById(id);
        return id;
    }

    @PostMapping("/origin")
    public Origin createOrigin(@RequestBody Origin origin) {
        return originRepository.save(origin);
    }

    @GetMapping("/origins")
    public List<Origin> getAllOrigins() {
        return originRepository.findAll();
    }

    @GetMapping("/origin/{id}")
    public Origin getOriginById(@PathVariable("id") Long id) {
        return originRepository.getOne(id);
    }

    @PutMapping("/origin/{id}")
    public Origin updateOriginById(@PathVariable("id") Long id, @RequestBody Origin
            origin) {
        origin.setId(id);
        return originRepository.save(origin);
    }

    @DeleteMapping("/origin/{id}")
    public Long deleteOriginById(@PathVariable("id") Long id) {
        originRepository.deleteById(id);
        return id;
    }
}
