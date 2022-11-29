package com.tying.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Tying
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable {

    private static final long serialVersionUID = -54979041104113736L;

    @TableId
    private Long id;
    /**
     * 菜单名
     */
    private String menuName;
    /**
     * 路由地址
     */
    private String path;
    /**
     * 组件路径
     */
    private String component;
    /**
     * 显示状态（0显示，1隐藏）
     */
    private String visible;
    /**
     * 菜单状态（0启用，1停用）
     */
    private String status;
    /**
     * 权限标识
     */
    private String perms;
    /**
     * 菜单图标
     */
    private String icon;

    private Long createdBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;
    /**
     * 是否删除（0未删除，1已删除）
     */
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;

}
