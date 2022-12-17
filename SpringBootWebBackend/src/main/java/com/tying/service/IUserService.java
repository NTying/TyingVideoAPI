package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.User;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUserService extends IService<User> {


    ResponseResult baseUserInfo(Long id);

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult baseUserInfoList(List<Long> ids);

    ResponseResult fullUserInfo();
}
