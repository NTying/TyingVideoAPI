package com.tying.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author Tying
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class InformationVo {

    //资讯id
    @TableId(type = IdType.AUTO)
    private Long id;
    //标题
    private String title;
    //布局类型
    private Long type;
    //评论数
    private Long commentCount;
    //资源上传者ID
    private Long createdBy;
    //资源上传时间
    private LocalDateTime createTime;
}
