package com.tying.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Video)表实体类
 *
 * @author makejava
 * @since 2022-12-06 21:16:58
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ty_video")
public class Video implements Serializable{
    private static final long serialVersionUID=-87036759076873602L;

    @TableId(type = IdType.AUTO)
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
    //缩略图
    private String thumbnail;
    //是否置顶（0 否，1 是）
    private String isTop;
    //状态（0 已上映， 1 预告）
    private String status;
    //观看次数
    private Long viewCount;
    //点赞数
    private Long likeCount;
    //收藏数
    private Long collectionCount;
    //评论数
    private Long commentCount;
    //是否允许评论（0 否，1 是）
    private String isComment;
    //资源上传者ID
    private Long createdBy;
    //资源上传时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //资源更新者ID
    private Long updateBy;
    //资源更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //删除标志（0 未删除，1 已删除）
    private Integer delFlag;

    public Video(long id, long viewCount, long likeCount, long collectionCount, long commentCount) {
        this.id = id;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.collectionCount = collectionCount;
        this.commentCount = commentCount;
    }
}

