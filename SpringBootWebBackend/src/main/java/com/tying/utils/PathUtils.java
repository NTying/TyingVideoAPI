package com.tying.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Tying
 * @version 1.0
 */
public class PathUtils {

    public static String generateFilePath(String fileName) {

        // 获取用户 Id
        Long userId = SecurityUtils.getUserId();
        // 根据日期生成路径
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        // UUID 作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuilder().append(userId).append(datePath).append(uuid).append(fileType).toString();
    }
}
