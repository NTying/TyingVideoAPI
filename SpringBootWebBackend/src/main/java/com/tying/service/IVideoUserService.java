package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.VideoUser;

/**
 * (VideoUser)表服务接口
 *
 * @author makejava
 * @since 2022-12-15 11:00:52
 */
public interface IVideoUserService extends IService<VideoUser> {

    ResponseResult getUserInteractVideo(Integer type, Integer pageNum, Integer limit);

    ResponseResult ifInteractWithCurrentUser(Long videoId);

    ResponseResult interactWithCurrentUser(Long videoId, int type, int value);
}

