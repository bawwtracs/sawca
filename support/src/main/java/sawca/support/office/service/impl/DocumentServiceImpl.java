package sawca.support.office.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sawca.support.office.entity.Document;
import sawca.support.office.mapper.DocumentMapper;
import sawca.support.office.service.IDocumentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2019-12-30
 */
@Service
public class DocumentServiceImpl extends ServiceImpl<DocumentMapper, Document> implements IDocumentService {

}
