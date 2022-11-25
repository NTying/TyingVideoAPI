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
public class JsonRedisUtils<E> extends BaseRedisUtils<E> {
    @Resource
    private RedisTemplate<String, E> jsonRedisTemplate;

    @PostConstruct
    public void init() {
        setRedisTemplate(jsonRedisTemplate);
    }

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return jsonRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return jsonRedisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key
     * @param entryKey
     * @param by
     * @return
     */
    public double hincr(String key, String entryKey, Double by) {
        return jsonRedisTemplate.opsForHash().increment(key, entryKey, by);
    }

    /**
     * hash递减
     *
     * @param key
     * @param entryKey
     * @param by
     * @return
     */
    public double hdecr(String key, String entryKey, Double by) {
        return jsonRedisTemplate.opsForHash().increment(key, entryKey, -by);
    }

}

