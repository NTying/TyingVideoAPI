package com.tying.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 2611556444074013268L;

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 角色权限字符串
     */
    private String role_key;

    /**
     * 角色状态（0启用，1停用）
     */
    private Character status;

    /**
     * 是否删除（0未删除，1已删除）
     */
    private Integer del_flag;

    /**
     * 创建人的用户id
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private Long updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
}
