package com.tying.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Tying
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class CategoryVo {

    private Long id;

    //类别名称
    private String name;
    //父分类ID，如果没有父分类，值为-1
    private Long pid;
}
