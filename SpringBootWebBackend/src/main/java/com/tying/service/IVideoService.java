package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Video;

/**
 * ( tyMovie)表服务接口
 *
 * @author makejava
 * @since 2022-12-02 16:25:10
 */
public interface IVideoService extends IService<Video> {

    ResponseResult updateInteractCount(Long videoId, Integer type, boolean flagInteract);

    ResponseResult saveVideoInfo(Video video);

    ResponseResult getVideoList(Integer pageNum, Integer limit);

    ResponseResult getVideoListByCategoryId(Integer pageNum, Integer limit, Long id);

    ResponseResult getMyCollectVideos(Integer pageNum, Integer limit);
}

