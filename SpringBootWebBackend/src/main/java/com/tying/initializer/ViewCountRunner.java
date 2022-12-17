package com.tying.initializer;

import com.tying.constants.RedisKeyConstant;
import com.tying.domain.entity.Video;
import com.tying.service.IVideoService;
import com.tying.utils.JsonRedisUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Tying
 * @version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private IVideoService videoService;

    @Resource
    //private JsonRedisUtils<Map<String, Integer>> jsonRedisUtils;
    private JsonRedisUtils<Integer> jsonRedisUtils;

    @Override
    public void run(String... args) throws Exception {

        // 查询视频信息
        List<Video> videoList = videoService.list();
        //Map<String, Map<String,Integer>> videoInteractCountList = new HashMap<>();
        for (Video video : videoList) {
            Map<String, Integer> videoInteractCount = new HashMap<>();
            videoInteractCount.put("ViewCount", video.getViewCount().intValue());
            videoInteractCount.put("LikeCount", video.getLikeCount().intValue());
            videoInteractCount.put("CollectionCount", video.getCollectionCount().intValue());
            videoInteractCount.put("CommentCount", video.getCommentCount().intValue());
            //videoInteractCountList.put(RedisKeyConstant.VIDEO_INTERACT_COUNT+video.getId().toString(), videoInteractCount);
            jsonRedisUtils.setMap(RedisKeyConstant.VIDEO_INTERACT_COUNT+video.getId()+"", videoInteractCount);
        }

        // 存储到 Redis 中

        //jsonRedisUtils.setMultiVal(videoInteractCountList);
    }
}
