package sawca.support.image.service;

import sawca.support.image.entity.RequestImage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hjl
 * @since 2019-12-09
 */
public interface IRequestImageService extends IService<RequestImage> {

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
