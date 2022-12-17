package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.service.IVideoUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/app/video")
public class VideoUserController {

    @Resource
    private IVideoUserService videoUserService;

    @GetMapping("/getUserInteractVideo")
    public ResponseResult getUserInteractVideo(Integer type, Integer pageNum, Integer limit) {
        return videoUserService.getUserInteractVideo(type, pageNum, limit);
    }

    @GetMapping("/flagsInteractWithCurrentUser")
    public ResponseResult ifInteractWithCurrentUser(Long videoId) {
        return videoUserService.ifInteractWithCurrentUser(videoId);
    }

    @PostMapping("/interactWithCurrentUser")
    public ResponseResult interactWithCurrentUser(@RequestBody Map<String, Object> param) {
        Long videoId = Long.parseLong(param.get("videoId").toString());
        int type = (int)param.get("type");
        int value = (int) param.get("value");
        return videoUserService.interactWithCurrentUser(videoId, type, value);
    }
}