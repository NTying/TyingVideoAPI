package com.tying.service.impl;

import com.tying.domain.User;
import com.tying.mapper.IUserMapper;
import com.tying.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public List<User> findAll() {
        return null;
    }
}
