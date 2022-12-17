package com.tying;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Tying
 * @version 1.0
 */
@SpringBootTest
public class ThirdPartAPITest {

    @Value("${oss.accessKey}")
    private String accessKey;

    @Value(value = "${oss.secretKey}")
    private String secretKey;

    @Value(value = "${oss.bucket}")
    private String bucket;

    @Test
    public void testStreamUpload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
/*        String accessKey = "8zwVIdxxSdFagmaYdIH1IKw1WE3-7S8L9hJDpuXO";
        String secretKey = "u_diTp6PvjqJv9zzK2fSyCIkTQ2Jt7jyW4PaRA7I";
        String bucket = "tying-video";*/
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        // 准确来说这里设置的是文件的全路径（目录 + 文件名 + 后缀），没有目录就放在根目录下
        // String key = "HelloWorld";
        String key = "2022/12/01/HelloWorld.md";
        try {
/*            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);*/

            InputStream fileInputStream = new FileInputStream("G:\\JavaThings\\sangeng\\JavaSE\\HelloWorld.md");

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(fileInputStream, key, upToken, null, null);
                //解析上传成功的结果
                // DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                ObjectMapper objectMapper = new ObjectMapper();
                DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
        }
    }
}
