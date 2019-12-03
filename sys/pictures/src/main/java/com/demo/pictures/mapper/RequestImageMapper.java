package com.demo.pictures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.pictures.entity.RequestImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
@Mapper
public interface RequestImageMapper extends BaseMapper<RequestImage> {

    List<Map<String, Object>> statisticsRequestImage();

    Long countRequestImage();

}
