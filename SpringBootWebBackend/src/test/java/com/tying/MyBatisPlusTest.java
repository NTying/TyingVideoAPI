package com.tying;

import com.tying.domain.entity.User;
import com.tying.mapper.IUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@SpringBootTest
public class MyBatisPlusTest {

    @Resource
    private IUserMapper userMapper;

    @Test
    public void testAutoFill() {
        User user = new User();
        user.setUserName("tying.wing");
        user.setNickName("会飞的猪");
        user.setSex("1");
        user.setPassword("$2a$10$5Hq074vU5hFKWmpeHwMKhe5c8oGb5bBYroUnWxR0GLZs/YAoFHBpC");

        userMapper.insert(user);
    }

    @Test
    public void testLogicDel() {
        // 不会真的删除记录，只是将 del_flag 值设为 1
        userMapper.deleteById(1L);
    }
}
