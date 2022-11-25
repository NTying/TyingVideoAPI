package com.tying.controller;

import com.tying.domain.ResponseResult;
import com.tying.domain.User;
import com.tying.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tying
 * @version 1.0
 */
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        ResponseResult responseResult = loginService.login(user);
        return responseResult;
    }
}
