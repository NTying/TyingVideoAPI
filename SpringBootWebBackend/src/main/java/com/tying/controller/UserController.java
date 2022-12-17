package com.tying.controller;

import com.tying.annotation.SystemLog;
import com.tying.domain.ResponseResult;
import com.tying.domain.entity.User;
import com.tying.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
@RequestMapping("/app")
@SuppressWarnings("all")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/user/full")
    public ResponseResult fullUserInfo() {
        return userService.fullUserInfo();
    }

    /**
     * 所有用户都可以获取到其他用户的一般信息
     * @return
     */
    @GetMapping("/user/base")
    public ResponseResult baseUserInfo(Long id) {
        return userService.baseUserInfo(id);
    }

    @PostMapping("/user/base/list")
    public ResponseResult baseUserInfoList(@RequestBody Map<String, List<Long>> ids) {
        return userService.baseUserInfoList(ids.get("id"));
    }
    /**
     * 需要登录后才能更新，所以请求头中要带上 token
     * @param user：更新后的用户信息实体
     * @return
     */
    @PutMapping("/user/modify")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user) {

        return userService.updateUserInfo(user);
    }

    /**
     * 注册接口，不需要携带 token 的请求头
     * @param user
     * @return
     */
    @PostMapping("/user/register")
    public ResponseResult register(@RequestBody User user) {

        return userService.register(user);
    }
}
