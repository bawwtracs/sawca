package sawca.support.vocabulary.service.impl;

import sawca.support.vocabulary.entity.Word;
import sawca.support.vocabulary.mapper.WordMapper;
import sawca.support.vocabulary.service.IWordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2020-01-09
 */
@Service
public class WordServiceImpl extends ServiceImpl<WordMapper, Word> implements IWordService {

}
