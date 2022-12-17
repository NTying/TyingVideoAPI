package com.tying.service;

import com.tying.domain.ResponseResult;
import com.tying.domain.entity.User;

/**
 * @author Tying
 * @version 1.0
 */
public interface ILoginService {
    /**
     * 登录接口
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 注销登录
     * @return
     */
    ResponseResult logout();
}
