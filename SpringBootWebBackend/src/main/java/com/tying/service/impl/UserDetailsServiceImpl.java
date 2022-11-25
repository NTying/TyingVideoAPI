package com.tying.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tying.domain.LoginUser;
import com.tying.domain.User;
import com.tying.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * SpringSecurity 拦截器链的 UserDetailsService 接口实现类的替换类
 * @author Tying
 * @version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);

        // 如果没有查询到用户就抛出异常
        // SpringSecurity 的拦截器链有一个专门处理异常的拦截器
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }

        // TODO 查询对应的权限信息

        // 把数据封装成 UserDetails 返回
        return new LoginUser(user);
    }
}
