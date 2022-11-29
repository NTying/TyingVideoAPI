package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.domain.User;
import com.tying.resolver.CurrentUserId;
import com.tying.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
@SuppressWarnings("all")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/all")
    // @CrossOrigin 在 CorsConfig 配置类中配置了跨域请求设置，所以这里不用使用注解实现
    public ResponseResult findAll(@CurrentUserId String userId) {

        System.out.println(userId);
        List<User> users = userService.list();

        return new ResponseResult(200, users);
    }
}
