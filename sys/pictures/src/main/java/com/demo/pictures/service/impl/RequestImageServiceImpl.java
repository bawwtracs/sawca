package com.demo.pictures.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.pictures.entity.RequestImage;
import com.demo.pictures.mapper.RequestImageMapper;
import com.demo.pictures.service.IRequestImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
@Service
public class RequestImageServiceImpl extends ServiceImpl<RequestImageMapper, RequestImage> implements IRequestImageService {

    @Resource
    private RequestImageMapper requestImageMapper;

    @Override
    public List<Map<String, Object>> statisticsRequestImage() {
        return requestImageMapper.statisticsRequestImage();
    }

    @Override
    public Long countRequestImage() {
        return requestImageMapper.countRequestImage();
    }
}
