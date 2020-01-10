package sawca.support.image.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sawca.support.image.entity.Request;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjl
 * @since 2019-12-09
 */
public interface IRequestService extends IService<Request> {

    Long addCount();

    Long fastAddCount();

    Long accurateAddCount();

    Long latestFastAddCount();

    Long latestAccurateAddCount();

    Long latestFastAddAvgTime();

    Long latestFastSearchAvgTime();

    Long latestAccurateAddAvgTime();

    Long latestAccurateSearchAvgTime();

    Long fastAddAvgTime();

    Long accurateAddAvgTime();

    Long fastSearchAvgTime();

    Long accurateSearchAvgTime();

}
