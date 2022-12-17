package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Video;
import com.tying.service.IVideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/app/video")
@SuppressWarnings("all")
public class VideoController {

    @Resource
    private IVideoService videoService;

    @PostMapping("/updateInteractCount")
    public ResponseResult updateInteractCount(@RequestBody Map<String, Object> params) {

        Long videoId = Long.parseLong(params.get("videoId").toString());
        int type = (int) params.get("type");
        boolean flagInteract = (boolean) params.get("flagInteract");
        return videoService.updateInteractCount(videoId, type, flagInteract);
    }

    @PostMapping("/saveVideoInfo")
    public ResponseResult saveVideoInfo(@RequestBody Video video) {

        return videoService.saveVideoInfo(video);
    }

    @GetMapping("/list")
    public ResponseResult getVideoList(Integer pageNum, Integer limit) {

        return videoService.getVideoList(pageNum, limit);
    }

    @GetMapping("/getByCategory")
    public ResponseResult getVideoListByCategoryId(Integer pageNum, Integer limit, Long categoryId) {

        return videoService.getVideoListByCategoryId(pageNum, limit, categoryId);
    }

    @GetMapping("/getMyCollectVideos")
    public ResponseResult getMyCollectVideos(Integer pageNum, Integer limit) {

        return videoService.getMyCollectVideos(pageNum, limit);
    }
}
