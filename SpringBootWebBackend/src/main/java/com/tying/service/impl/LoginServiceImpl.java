package com.tying.service.impl;

import com.tying.domain.LoginUser;
import com.tying.domain.ResponseResult;
import com.tying.domain.User;
import com.tying.service.ILoginService;
import com.tying.utils.JsonRedisUtils;
import com.tying.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Tying
 * @version 1.0
 */
@Service
@SuppressWarnings("all")
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

/*    @Autowired
    private RedisCache redisCache;*/

    @Resource
    private JsonRedisUtils<LoginUser> loginUserJsonRedisUtils;

    @Override
    public ResponseResult login(User user) {
        // 调用 AuthenticationManager 的 authenticate 方法进行用户认证
        // 这里会调用 UserDetailsService 接口的实现类
        // authenticate 对象中有一个 principal 属性，存放的是 UserDetailsService 实现类 loadUserByUsername 方法的返回值
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        // 如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }

        // 如果认证通过，使用 userId 生成一个 jwt
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long id = loginUser.getUser().getId();
        String jwt = JwtUtils.createJWT(id.toString());

        HashMap<String, String> map = new HashMap<>(1);
        map.put("token", jwt);
        // 把完整的用户信息存入 redis， userId 作为 key
        //redisCache.setCacheObject("login:" + id, loginUser);
        loginUserJsonRedisUtils.setValue("login:" + id, loginUser);

        return new ResponseResult(200, "登陆成功", map);
    }
}
