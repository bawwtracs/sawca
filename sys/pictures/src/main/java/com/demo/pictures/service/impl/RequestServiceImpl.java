package com.demo.pictures.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.pictures.entity.Request;
import com.demo.pictures.mapper.RequestMapper;
import com.demo.pictures.service.IRequestService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hjl
 * @since 2019-11-28
 */
@Service
public class RequestServiceImpl extends ServiceImpl<RequestMapper, Request> implements IRequestService {

}
