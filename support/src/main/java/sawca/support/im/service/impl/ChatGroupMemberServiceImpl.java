package sawca.support.im.service.impl;

import sawca.support.im.entity.ChatGroupMember;
import sawca.support.im.mapper.ChatGroupMemberMapper;
import sawca.support.im.service.IChatGroupMemberService;
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
public class ChatGroupMemberServiceImpl extends ServiceImpl<ChatGroupMemberMapper, ChatGroupMember> implements IChatGroupMemberService {

}
