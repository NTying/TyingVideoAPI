package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.InformationThumb;

/**
 * (InformationThumb)表服务接口
 *
 * @author makejava
 * @since 2022-12-12 15:39:42
 */
public interface IInformationThumbService extends IService<InformationThumb> {

    ResponseResult getInformationThumb(Long infoId);
}

