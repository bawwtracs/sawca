package sawca.support.image.mapper;

import sawca.support.image.entity.RequestImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hjl
 * @since 2019-12-09
 */
public interface RequestImageMapper extends BaseMapper<RequestImage> {

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
