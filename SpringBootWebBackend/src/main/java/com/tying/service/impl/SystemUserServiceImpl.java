package com.tying.service.impl;

import com.tying.domain.SystemUser;
import com.tying.mapper.ISystemUserMapper;
import com.tying.service.ISystemUserService;
import com.tying.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Tying
 * @version 1.0
 */
@Service
public class SystemUserServiceImpl implements ISystemUserService {

    @Autowired
    private ISystemUserMapper systemUserMapper;

    @Override
    public SystemUser login(SystemUser user) {
        SystemUser loginUser = systemUserMapper.login(user);
        return loginUser;
    }
}
