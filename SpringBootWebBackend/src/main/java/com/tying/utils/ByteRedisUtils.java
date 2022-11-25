package com.tying.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@Component
public class ByteRedisUtils<E> extends BaseRedisUtils<E> {

    @Resource
    private RedisTemplate<String, E> byteRedisTemplate;

    @PostConstruct
    public void init() {
        setRedisTemplate(byteRedisTemplate);
    }

}

