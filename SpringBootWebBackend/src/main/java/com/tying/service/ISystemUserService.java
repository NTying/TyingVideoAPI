package com.tying.service;

import com.tying.domain.SystemUser;

/**
 * @author Tying
 * @version 1.0
 */
public interface ISystemUserService {

    /**
     * 登录业务
     * @param user
     * @return
     */
    SystemUser login(SystemUser user);
}
