package com.demo.pictures.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.pictures.entity.Request;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
@Mapper
public interface RequestMapper extends BaseMapper<Request> {

}
