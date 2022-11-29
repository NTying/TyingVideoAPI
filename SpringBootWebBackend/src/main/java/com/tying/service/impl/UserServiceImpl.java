package com.tying.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tying.domain.User;
import com.tying.mapper.IUserMapper;
import com.tying.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 如果继承了 ServiceImpl，指定了泛型，就不用使用 @AutoWired 手动注入 IUserMapper 实现类对象
 * 当然如果需要其他 Mapper 对象，就要手动注入
 * @author Tying
 * @version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
    @Override
    public List<User> selfDefFunc() {
        IUserMapper userMapper = getBaseMapper();
        List<User> users = userMapper.selectList(null);
        return users;
    }
}
