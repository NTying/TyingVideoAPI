package com.tying.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Tying
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class UserBaseInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;

}