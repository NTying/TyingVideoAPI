package com.tying.service;

import com.tying.domain.ResponseResult;
import com.tying.domain.User;

/**
 * @author Tying
 * @version 1.0
 */
public interface ILoginService {

    ResponseResult login(User user);
}
