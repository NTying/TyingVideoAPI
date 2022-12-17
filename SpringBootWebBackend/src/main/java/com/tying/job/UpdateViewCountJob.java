package com.tying.job;

import com.tying.domain.entity.Video;
import com.tying.service.IVideoService;
import com.tying.utils.JsonRedisUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Tying
 * @version 1.0
 */
@Component
public class UpdateViewCountJob {

    @Resource
    private JsonRedisUtils<Integer> jsonRedisUtils;

    @Resource
    private IVideoService videoService;

    @Scheduled(cron = "6 * * * * ?")
    public void updateViewCount() {

        // 获取交互相关信息的的RedisKey
        Set<String> keys = jsonRedisUtils.getKeys("videoInteractCount:*");
        // 获取 redis 中的记录的各个Video交互相关的信息的数量
        List<Video> updateVideos = new ArrayList<>();
        for (String key : keys) {
            Map<String, Integer> map = jsonRedisUtils.getMap(key);
            Video video = new Video(Long.valueOf(key.substring(key.length()-1)),
                    map.get("ViewCount"),
                    map.get("LikeCount"),
                    map.get("CollectionCount"),
                    map.get("CommentCount"));
            updateVideos.add(video);
        }
        videoService.updateBatchById(updateVideos);

    }
}
