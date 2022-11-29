package com.tying.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tying.domain.User;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUserService extends IService<User> {

    List<User> selfDefFunc();
}
