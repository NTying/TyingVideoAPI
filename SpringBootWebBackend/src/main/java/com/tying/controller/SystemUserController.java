package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.domain.SystemUser;
import com.tying.service.impl.SystemUserServiceImpl;
import com.tying.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Tying
 * @version 1.0
 */
@RequestMapping("/sys_user")
@RestController
public class SystemUserController {

    @Autowired
    private SystemUserServiceImpl systemUserService;
    Map<String, Object> map;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody SystemUser user) {

        SystemUser loginUser = systemUserService.login(user);
        if (loginUser != null) {
            String jwt = JwtUtils.createJWT(UUID.randomUUID().toString(), loginUser.getId().toString(), null);
            map = new HashMap<>(8);
            map.put("token", jwt);
        } else {
            return new ResponseResult(300, "用户名或者密码错误，请重新登录");
        }
        return new ResponseResult(200, "登陆成功", map);
    }
}
