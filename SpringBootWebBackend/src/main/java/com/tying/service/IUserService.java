package com.tying.service;

import com.tying.domain.User;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
public interface IUserService {

    /**
     * 查询所有的 User 数据
     * @return
     */
    public List<User> findAll();
}
