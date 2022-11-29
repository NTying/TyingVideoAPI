package com.tying;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tying.domain.User;
import com.tying.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tying
 * @version 1.0
 */
@SpringBootTest
public class ServiceTest {

    @Resource
    private IUserService userService;

    @Test
    public void testUserService() {
        List<User> users = userService.list();
        System.out.println(users);

        Page<User> page = new Page<>();
        page.setSize(2);
        page.setCurrent(1);
        Page<User> userPage = userService.page(page);
        System.out.println(userPage.getRecords());
    }
}
