package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.service.IUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
public class UploadController {

    @Resource
    private IUploadService uploadService;

    @PostMapping("/uploadImg")
    public ResponseResult uploadImg(@RequestBody MultipartFile img) {

        ResponseResult responseResult = uploadService.uploadImg(img);
        return responseResult;
    }

    @PostMapping("/uploadVideo")
    public ResponseResult uploadVideo(@RequestBody MultipartFile video, String title, String summary) {

        ResponseResult responseResult = uploadService.uploadVideo(video, title, summary);
        return responseResult;
    }
}
