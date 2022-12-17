package com.tying.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.tying.callback.IOssCallBack;
import com.tying.constants.AppHttpCodeEnum;
import com.tying.constants.OSSConstants;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.Video;
import com.tying.exception.SystemException;
import com.tying.service.IUploadService;
import com.tying.service.IVideoService;
import com.tying.utils.PathUtils;
import org.intellij.lang.annotations.RegExp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * @author Tying
 * @version 1.0
 */
@Service
public class OssUploadServiceImpl implements IUploadService {

    private ResponseResult responseResult;

    @Resource
    private IVideoService videoService;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {

        // todo 判断文件类型或者文件大小
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();
        // 对原始文件名进行判断
        if (!originalFilename.endsWith(OSSConstants.FileType.JPG.getValue())) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 如果判断通过，上传文件到 OSS
        String filePath = PathUtils.generateFilePath(originalFilename);

        uploadSmallFile(img, filePath, new IOssCallBack() {
            @Override
            public void onSuccess(String result) {
                responseResult = ResponseResult.okResult(result);
            }

            @Override
            public void onFailure(Exception e) {
                responseResult = ResponseResult.errorResult(AppHttpCodeEnum.OSS_UPLOAD_ERROR, e.getMessage());
            }
        });
        return responseResult;
    }

    @Override
    public ResponseResult uploadVideo(MultipartFile video, String title, String summary) {
        // todo 判断文件类型或者文件大小
        // 获取原始文件名
        String originalFilename = video.getOriginalFilename();
        // 对原始文件名进行判断
        if (!originalFilename.endsWith(OSSConstants.FileType.MP4.getValue())) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        // 如果判断通过，上传文件到 OSS
        String filePath = PathUtils.generateFilePath(originalFilename);

        uploadBigFile(video, filePath, new IOssCallBack() {
            @Override
            public void onSuccess(String result) {
                Video videoObj = new Video();
                videoObj.setTitle(title);
                videoObj.setSummary(summary);
                videoObj.setResUrl(result);
                responseResult = videoService.saveVideoInfo(videoObj);
            }

            @Override
            public void onFailure(Exception e) {
                responseResult = ResponseResult.errorResult(AppHttpCodeEnum.OSS_UPLOAD_ERROR, e.getMessage());
            }
        });
        return responseResult;
    }

    /**
     * 从 yml 文件中获取上传凭证
     */
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value(value = "${oss.secretKey}")
    private String secretKey;
    @Value(value = "${oss.bucket}")
    private String bucket;

    /**
     * @param file
     * @param filePath: 不指定的情况下，以文件内容的hash值作为文件名，放在根目录下
     * @return
     */
    public void uploadSmallFile(MultipartFile file, String filePath, IOssCallBack callBack) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            InputStream fileInputStream = file.getInputStream();
/*            StringMap putPolicy = new StringMap();
            putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
            putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"fsize\":$(fsize)}");
            putPolicy.put("callbackBodyType", "application/json");*/
            Response response = uploadManager.put(fileInputStream, filePath, upToken, null, null);
            //解析上传成功的结果(Jackson)
            ObjectMapper objectMapper = new ObjectMapper();
            DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);

            callBack.onSuccess(putRet.key);
            //return putRet.key;
        } catch (IOException ex) {
/*                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }*/
            callBack.onFailure(ex);
        }
        //return "fail";
    }

    public void uploadBigFile(MultipartFile file, String filePath, IOssCallBack callBack) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // 设置分片上传并发，1：采用同步上传；大于1：采用并发上传
        cfg.resumableUploadMaxConcurrentTaskCount = 2;
        // TODO 其他参数参考类注释

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            InputStream fileInputStream = file.getInputStream();

            // 设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(fileInputStream, filePath, upToken, null, null);

                //解析上传成功的结果(Jackson)
                ObjectMapper objectMapper = new ObjectMapper();
                DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
                callBack.onSuccess(putRet.key);
            } catch (QiniuException ex) {
                callBack.onFailure(ex);
            }
        } catch (IOException ex) {
            callBack.onFailure(ex);
        }
    }
}
