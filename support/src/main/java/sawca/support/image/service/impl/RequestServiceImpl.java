package sawca.support.image.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import sawca.support.image.entity.Request;
import sawca.support.image.mapper.RequestImageMapper;
import sawca.support.image.mapper.RequestMapper;
import sawca.support.image.service.IRequestService;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2019-12-09
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

    @Resource
    private RequestImageMapper requestImageMapper;

    @Override
    public Long addCount() {
        return requestImageMapper.addCount();
    }

    @Override
    public Long fastAddCount() {
        return requestImageMapper.fastAddCount();
    }

    @Override
    public Long accurateAddCount() {
        return requestImageMapper.accurateAddCount();
    }

    @Override
    public Long latestFastAddCount() {
        return requestImageMapper.latestFastAddCount();
    }

    @Override
    public Long latestAccurateAddCount() {
        return requestImageMapper.latestAccurateAddCount();
    }

    @Override
    public Long latestFastAddAvgTime() {
        return requestImageMapper.latestFastAddAvgTime();
    }

    @Override
    public Long latestFastSearchAvgTime() {
        return requestImageMapper.latestFastSearchAvgTime();
    }

    @Override
    public Long latestAccurateAddAvgTime() {
        return requestImageMapper.latestAccurateAddAvgTime();
    }

    @Override
    public Long latestAccurateSearchAvgTime() {
        return requestImageMapper.latestAccurateSearchAvgTime();
    }

    @Override
    public Long fastAddAvgTime() {
        return requestImageMapper.fastAddAvgTime();
    }

    @Override
    public Long accurateAddAvgTime() {
        return requestImageMapper.accurateAddAvgTime();
    }

    @Override
    public Long fastSearchAvgTime() {
        return requestImageMapper.fastSearchAvgTime();
    }

    @Override
    public Long accurateSearchAvgTime() {
        return requestImageMapper.accurateSearchAvgTime();
    }
}
