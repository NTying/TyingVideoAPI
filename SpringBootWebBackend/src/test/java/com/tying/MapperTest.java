package com.tying;

import com.tying.domain.User;
import com.tying.mapper.IUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
@SpringBootTest
public class MapperTest {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testBCryptPasswordEncoder() {
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("1234");
        String encode1 = passwordEncoder.encode("1234");
        boolean matches = passwordEncoder.matches("1234", "$2a$10$5Hq074vU5hFKWmpeHwMKhe5c8oGb5bBYroUnWxR0GLZs/YAoFHBpC");
        System.out.println(matches);
        System.out.println(encode);
        System.out.println(encode1);
    }
}
