package com.tying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.constants.InteractType;
import com.tying.constants.RedisKeyConstant;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Video;
import com.tying.domain.entity.VideoUser;
import com.tying.domain.vo.VideoVo;
import com.tying.mapper.IVideoMapper;
import com.tying.service.IVideoService;
import com.tying.service.IVideoUserService;
import com.tying.utils.BeanCopyUtils;
import com.tying.utils.JsonRedisUtils;
import com.tying.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ( tyMovie)表服务实现类
 *
 * @author makejava
 * @since 2022-12-02 16:25:10
 */
@Service
public class VideoServiceImpl extends ServiceImpl<IVideoMapper, Video> implements IVideoService {

    @Resource
    private IVideoMapper videoMapper;
    @Resource
    private IVideoUserService videoUserService;

    @Resource
    private JsonRedisUtils<Integer> jsonRedisUtils;

    @Override
    public ResponseResult updateInteractCount(Long videoId, Integer type, boolean flagInteract) {

        // 获取当前用户的 id（解析 token）
        Long userId = SecurityUtils.getUserId();

        boolean[] result = (boolean[])videoUserService.ifInteractWithCurrentUser(videoId).getResult();
        String entryKey = null;
        if (type == InteractType.COMMENT) {
            entryKey = "CommentCount";
        }
        if (type == InteractType.COLLECT) {
            entryKey = "CollectionCount";
            if (flagInteract) {
                jsonRedisUtils.hdecr(RedisKeyConstant.VIDEO_INTERACT_COUNT + videoId + "", entryKey, 1);
            } else {
                jsonRedisUtils.hincr(RedisKeyConstant.VIDEO_INTERACT_COUNT + videoId + "", entryKey, 1);
            }
        }
        if (type == InteractType.LIKE) {
            entryKey = "LikeCount";
            if (flagInteract){
                jsonRedisUtils.hdecr(RedisKeyConstant.VIDEO_INTERACT_COUNT + videoId + "", entryKey, 1);
            } else {
                jsonRedisUtils.hincr(RedisKeyConstant.VIDEO_INTERACT_COUNT + videoId + "", entryKey, 1);
            }
        }

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult saveVideoInfo(Video video) {

        save(video);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getVideoList(Integer pageNum, Integer limit) {

        IPage<Video> page = new Page<>();
        // 设置每页条数
        page.setSize(limit);
        // 设置查询第几页
        page.setCurrent(pageNum);
        IPage<Video> videoIPage = videoMapper.selectPage(page, null);
        List<Video> videoList = videoIPage.getRecords();
        List<VideoVo> videoVoList = BeanCopyUtils.copyBeanList(videoList, VideoVo.class);

        return ResponseResult.okResult(videoVoList);
    }

    @Override
    public ResponseResult getVideoListByCategoryId(Integer pageNum, Integer limit, Long id) {

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", id);

        IPage<Video> page = new Page<>();
        // 设置每页条数
        page.setSize(limit);
        // 设置查询第几页
        page.setCurrent(pageNum);
        IPage<Video> videoIPage = videoMapper.selectPage(page, queryWrapper);
        List<Video> videoList = videoIPage.getRecords();
        List<VideoVo> videoVoList = BeanCopyUtils.copyBeanList(videoList, VideoVo.class);
        for (Video video : videoList) {
            Integer commentCount = jsonRedisUtils.getHash(RedisKeyConstant.VIDEO_INTERACT_COUNT + video.getId() + "", "CommentCount");
            Integer collectionCount = jsonRedisUtils.getHash(RedisKeyConstant.VIDEO_INTERACT_COUNT + video.getId() + "", "CollectionCount");
            Integer likeCount = jsonRedisUtils.getHash(RedisKeyConstant.VIDEO_INTERACT_COUNT + video.getId() + "", "LikeCount");
            VideoVo videoVoRtn = videoVoList.stream().filter(videoVo -> videoVo.getId() == video.getId()).findFirst().get();
            videoVoRtn.setCommentCount(commentCount.longValue());
            videoVoRtn.setCollectionCount(collectionCount.longValue());
            videoVoRtn.setLikeCount(likeCount.longValue());
        }
        return ResponseResult.okResult(videoVoList);
    }

    @Override
    public ResponseResult getMyCollectVideos(Integer pageNum, Integer limit) {

        List<VideoUser> videoUsers = (List<VideoUser>) videoUserService.getUserInteractVideo(2, pageNum, limit).getResult();
        List<Long> videoIds = videoUsers.stream()
                .map(obj -> obj.getVideoId())
                .collect(Collectors.toList());

        QueryWrapper<Video> queryWrapper = new QueryWrapper();
        queryWrapper.in("id", videoIds);

        IPage<Video> page = new Page<>();
        // 设置每页条数
        page.setSize(limit);
        // 设置查询第几页
        page.setCurrent(pageNum);
        IPage<Video> videoIPage = videoMapper.selectPage(page, queryWrapper);
        List<Video> videoList = videoIPage.getRecords();
        List<VideoVo> videoVoList = BeanCopyUtils.copyBeanList(videoList, VideoVo.class);

        return ResponseResult.okResult(videoVoList);
    }
}

