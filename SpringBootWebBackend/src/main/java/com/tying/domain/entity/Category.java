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
 * (Category)表实体类
 *
 * @author makejava
 * @since 2022-12-11 21:17:35
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ty_category")
public class Category implements Serializable{
    private static final long serialVersionUID=-89310849794703251L;
    @TableId(type = IdType.AUTO)
    private Long id;

    //类别名称
    private String name;
    //父分类ID，如果没有父分类，值为-1
    private Long pid;
    //描述信息
    private String description;
    //状态：（0 正常，1 禁用）
    private String status;
    //创建用户的 id
    private Long createdBy;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    //更新用户的信息
    private Long updateBy;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    //删除标志：（0 未删除，1 已删除）
    private Integer delFlag;

}

