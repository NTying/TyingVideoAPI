package com.tying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.constants.InteractType;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.VideoUser;
import com.tying.mapper.IVideoUserMapper;
import com.tying.service.IVideoUserService;
import com.tying.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (VideoUser)表服务实现类
 *
 * @author makejava
 * @since 2022-12-15 11:00:52
 */
@Service("videoUserService")
public class VideoUserServiceImpl extends ServiceImpl<IVideoUserMapper, VideoUser> implements IVideoUserService {

    @Resource
    private IVideoUserMapper videoUserMapper;

    @Override
    public ResponseResult getUserInteractVideo(Integer type, Integer pageNum, Integer limit) {

        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();

        QueryWrapper<VideoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        if (type == InteractType.COMMENT) {
            queryWrapper.eq("is_comment", 1);
        }
        if (type == InteractType.COLLECT) {
            queryWrapper.eq("is_collect", 1);
        }
        if (type == InteractType.LIKE) {
            queryWrapper.eq("is_like", 1);
        }

        IPage<VideoUser> page = new Page<>();
        // 设置每页条数
        page.setSize(limit);
        // 设置查询第几页
        page.setCurrent(pageNum);
        IPage<VideoUser> videoIPage = videoUserMapper.selectPage(page, queryWrapper);
        List<VideoUser> videoList = videoIPage.getRecords();

        return ResponseResult.okResult(videoList);
    }

    @Override
    public ResponseResult ifInteractWithCurrentUser(Long videoId) {

        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();

        QueryWrapper<VideoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("video_id", videoId);
        queryWrapper.eq("is_comment", 1);
        boolean flagComment = count(queryWrapper) > 0;

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("video_id", videoId);
        queryWrapper.eq("is_collect", 1);
        boolean flagCollect = count(queryWrapper) > 0;

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("video_id", videoId);
        queryWrapper.eq("is_like", 1);
        boolean flagLike = count(queryWrapper) > 0;

        boolean[] flags = new boolean[]{flagComment, flagCollect, flagLike};
        return ResponseResult.okResult(flags);
    }

    @Override
    public ResponseResult interactWithCurrentUser(Long videoId, int type, int value) {

        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();
        // 判断是否曾点赞、收藏、评论过
        QueryWrapper<VideoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("video_id", videoId);
        boolean everInteract = count(queryWrapper) > 0;

        VideoUser videoUser = new VideoUser();
        videoUser.setUserId(userId);
        videoUser.setVideoId(videoId);
        if (type == InteractType.COMMENT) {
            videoUser.setIsComment(value);
        }
        if (type == InteractType.COLLECT) {
            videoUser.setIsCollect(value);
        }
        if (type == InteractType.LIKE) {
            videoUser.setIsLike(value);
        }

        if (everInteract) {
            update(videoUser, queryWrapper);
        } else {
            save(videoUser);
        }

        return ResponseResult.okResult();
    }
}

