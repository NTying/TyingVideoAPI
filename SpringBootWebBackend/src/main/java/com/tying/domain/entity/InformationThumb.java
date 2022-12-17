package com.tying.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (InformationThumb)表实体类
 *
 * @author makejava
 * @since 2022-12-12 16:14:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ty_information_thumb")
public class InformationThumb implements Serializable{
    private static final long serialVersionUID=-57155191403540606L;
    //缩略图id@TableId(type = IdType.AUTO)
    private Long thumbId;

    //缩略图url
    private String thumbUrl;
    //资讯id
    private Long informationId;

}

