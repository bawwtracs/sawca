package sawca.support.im.service.impl;

import sawca.support.im.entity.Message;
import sawca.support.im.mapper.MessageMapper;
import sawca.support.im.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2020-01-03
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
