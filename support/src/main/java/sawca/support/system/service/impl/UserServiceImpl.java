package sawca.support.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sawca.support.system.entity.User;
import sawca.support.system.mapper.UserMapper;
import sawca.support.system.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2020-01-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
