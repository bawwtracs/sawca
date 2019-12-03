package com.demo.pictures.service;

import com.demo.pictures.entity.RequestImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
public interface IRequestImageService extends IService<RequestImage> {

    List<Map<String,Object>> statisticsRequestImage();

    Long countRequestImage();
}
