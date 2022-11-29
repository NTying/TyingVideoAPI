package com.tying;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tying.domain.Menu;
import com.tying.domain.Role;
import com.tying.domain.User;
import com.tying.mapper.IMenuMapper;
import com.tying.mapper.IRoleMapper;
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
@SuppressWarnings("all")
public class MapperTest {

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IMenuMapper menuMapper;

    @Autowired
    private IRoleMapper roleMapper;
    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void testSelfDefFunc() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", 1);
        queryWrapper.eq("user_name", "tying");
        User user = userMapper.findMyUserByWrapper(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testPage() {
        IPage<User> page = new Page<>();
        // 设置每页条数
        page.setSize(2);
        // 设置查询第几页
        page.setCurrent(1);
        IPage<User> userIPage = userMapper.selectPage(page, null);
        System.out.println(userIPage.getRecords()); // 获取当前页的数据
        System.out.println(userIPage.getTotal());   // 获取总记录数
        System.out.println(userIPage.getCurrent()); // 获取当前页码
    }

    @Test
    public void testRole() {
/*        List<Role> menus = roleMapper.findAllRoles();
        System.out.println(menus);*/

        Page<Role> page = new Page<>();
        // 设置每页大小
        page.setSize(1);
        // 设置当前页码
        page.setCurrent(2);
        roleMapper.findRolesByPage(page);
        System.out.println(page.getRecords());
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

    @Test
    public void testMenuMapper() {
        List<String> perms = menuMapper.selectPermsByUserId(new Long(1));
        System.out.println(perms);
    }
}
