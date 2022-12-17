package com.tying.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Tying
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class VideoVo {

    private Long id;

    //标题
    private String title;
    //名字
    private String name;
    //资源地址
    private String resUrl;
    //剧情简介
    private String summary;
    //所属类别ID
    private Long categoryId;
    //观看次数
    private Long viewCount;
    //点赞数
    private Long likeCount;
    //收藏数
    private Long collectionCount;
    //评论数
    private Long commentCount;
    //缩略图
    private String thumbnail;
    //资源上传者ID
    private Long createdBy;

}
