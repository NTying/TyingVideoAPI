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
 * (Information)表实体类
 *
 * @author makejava
 * @since 2022-12-12 15:44:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ty_information")
public class Information implements Serializable{
    private static final long serialVersionUID=-77427758043558777L;
    //资讯id
    @TableId(type = IdType.AUTO)
    private Long id;

    //标题
    private String title;
    //概述
    private String summary;
    //所属类别ID
    private Long categoryId;
    //布局类型
    private Long type;
    //是否置顶（0 否，1 是）
    private String isTop;
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

}

