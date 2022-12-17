package com.tying.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (VideoUser)表实体类
 *
 * @author makejava
 * @since 2022-12-15 23:25:16
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ty_video_user")
public class VideoUser implements Serializable{
    private static final long serialVersionUID=836820441398128041L;
    //视频ID
    private Long videoId;
    //用户ID
    private Long userId;

    //标题
    private String title;
    //资源地址
    private String resUrl;
    //所属类别ID
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否已评论
    private Integer isComment;
    //是否已点赞
    private Integer isLike;
    //是否已收藏
    private Integer isCollect;

}

