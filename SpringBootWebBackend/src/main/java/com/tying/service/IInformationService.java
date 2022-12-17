package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Information;

/**
 * (Information)表服务接口
 *
 * @author makejava
 * @since 2022-12-12 14:37:20
 */
public interface IInformationService extends IService<Information> {

    ResponseResult getVideoList(Integer pageNum, Integer limit);
}

