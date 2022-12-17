package com.tying.service;

import com.tying.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUploadService {

    /**
     * 上传图片文件到 OSS
     * @param img
     * @return
     */
    ResponseResult uploadImg(MultipartFile img);

    ResponseResult uploadVideo(MultipartFile video, String title, String summary);
}
