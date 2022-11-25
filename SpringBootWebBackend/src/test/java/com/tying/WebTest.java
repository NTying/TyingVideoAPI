package com.tying;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Tying
 * @version 1.0
 */
@SpringBootTest
public class WebTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedisSet() {
        redisTemplate.opsForValue().set("name","Tying");
    }

    @Test
    public void testRedisGet() {
        String name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
