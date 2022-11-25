package com.tying.utils;

import lombok.Setter;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Tying
 * @version 1.0
 */
@Component
public class BaseRedisUtils<E> {

    /**
     * 过期时间一周
     */
    private static final long WEEK_SECONDS = 7 * 24 * 60 * 60;

    @Setter
    private RedisTemplate<String, E> redisTemplate;

    ///common///

    /**
     * 列举出所有的key的集合
     *
     * @return： Redis 中存放的所有 key
     */
    public Set<String> getKeys(String pattern) {
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key： Redis key
     * @param time： 失效时间
     * @return
     */
    public boolean setExpire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key： redis key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key： redis key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key： redis key
     */
    public boolean del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
            return true;
        }
        return false;
    }

    /**
     * 普通缓存放入，永不过期
     *
     * @param key： redis key
     * @param value
     * @return
     */
    public boolean setValue(String key, E value) {
        try {
            //boundValueOps()
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
     *
     * @param key：redis
     * @param value： 设置的新值
     * @return
     */
    public E getAndSetVal(String key, E value) {
        try {
            final E res = redisTemplate.opsForValue().getAndSet(key, value);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key： redis key
     * @param value
     * @param expireTime 如果值为null，则使用默认的一周过期；如果值小于0则永不过期
     * @return
     */
    public boolean setValue(String key, E value, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForValue().set(key, value, WEEK_SECONDS, TimeUnit.SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
                } else {
                    //永不过期
                    redisTemplate.opsForValue().set(key, value);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量添加普通缓存
     *
     * @param map：redis key 从 Map key取，value 从 Map value 从取
     * @return
     */
    public boolean setMultiVal(Map<String, E> map) {
        try {
            redisTemplate.opsForValue().multiSet(map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取普通缓存
     *
     * @param key： redis key
     * @return
     */
    public E getValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 批量获取普通缓存
     *
     * @param keys： redis key 集合
     * @return
     */
    public List<E> getMultiVal(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }


    ///Map/hash//

    /**
     * 缓存 Hash数据，永不过期
     *
     * @param key： redis key
     * @param entryKey： hash key
     * @param entryValue： hash value
     * @return
     */
    public boolean setHash(String key, String entryKey, E entryValue) {
        try {
            redisTemplate.opsForHash().put(key, entryKey, entryValue);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存 Hash 数据，可设置过期时间
     *
     * @param key: redis key
     * @param entryKey: hash key
     * @param entryValue: hash value
     * @param expireTime 如果值为null则使用默认一周不过期；如果<0则永不过期
     * @return
     */
    public boolean setHash(String key, String entryKey, E entryValue, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForHash().put(key, entryKey, entryValue);
                //默认过期时间
                setExpire(key, WEEK_SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForHash().put(key, entryKey, entryValue);
                    //使用指定的过期时间
                    setExpire(key, expireTime);
                } else {
                    //永不过期
                    setHash(key, entryKey, entryValue);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存 Hash （Map）数据，永不过期
     *
     * @param key: redis key
     * @param map: 要缓存的 Hash（Map）数据
     * @return
     */
    public boolean setMap(String key, Map<String, E> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 缓存 Hash （Map）数据，永不过期，并设置时间
     *
     * @param key： redis key
     * @param map： 要缓存的 Hash（Map）数据
     * @param expireTime 如果值为null则使用默认一周不过期；如果<0则永不过期
     * @return
     */
    public boolean setMap(String key, Map<String, E> map, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForHash().putAll(key, map);
                //默认过期时间
                setExpire(key, WEEK_SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForHash().putAll(key, map);
                    //使用指定的过期时间
                    setExpire(key, expireTime);
                } else {
                    //永不过期
                    setMap(key, map);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 Hash 数据
     *
     * @param key： redis key
     * @param entryKey： hash key
     * @return: hash value
     */
    public E getHash(String key, String entryKey) {
        final HashOperations<String, String, E> ops = redisTemplate.opsForHash();
        return ops.get(key, entryKey);
    }

    /**
     * 获取 HashKey 对应的所有键值
     *
     * @param key: hash key
     * @return: Map
     */
    public Map<String, E> getMap(String key) {
        final HashOperations<String, String, E> ops = redisTemplate.opsForHash();
        return ops.entries(key);
    }

    /**
     * 删除hash表中的值
     *
     * @param key: redis key
     * @param entryKey: hash key
     */
    public boolean delHash(String key, String... entryKey) {
        return redisTemplate.opsForHash().delete(key, entryKey) > 0;
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key: redis key
     * @param entryKey: hash key
     * @return
     */
    public boolean hasHashKey(String key, String entryKey) {
        return redisTemplate.opsForHash().hasKey(key, entryKey);
    }

    //set///

    /**
     * 缓存 Set 数据，永不过期
     *
     * @param key: redis key
     * @param values: 可变参数，会转换成 Set 集合缓存到 Redis
     * @return
     */
    public long setSet(String key, E... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存，可设置有效时间
     *
     * @param key： redis key
     * @param expireTime： 有效时间
     * @param values： 可变参数，会转换成 Set 集合缓存到 Redis
     * @return
     */
    public boolean setSet(String key, Long expireTime, E... values) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForSet().add(key, values);
                //默认过期时间
                setExpire(key, WEEK_SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForSet().add(key, values);
                    //默认过期时间
                    setExpire(key, expireTime);
                } else {
                    //永不过期
                    setSet(key, values);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 Set 数据
     *
     * @param key： redis key
     * @return
     */
    public Set<E> getAndSetVal(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询缓存的 Set 数据中是否存在某个 value
     *
     * @param key： redis key
     * @param value： Set value
     * @return
     */
    public boolean hasValOnSetCache(String key, E value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取缓存的 Set 的长度
     *
     * @param key： redis key
     * @return
     */
    public long getSetCacheSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除 Set 中存在于 values 集合中的元素
     *
     * @param key： redis key
     * @param values： 可变参数，要移除的存在于 Set 中的值
     * @return
     */
    public long delValFromSetCache(String key, E... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将value放入缓存，以 List 形式存储，永不过期
     *
     * @param key： redis key
     * @param value： value
     * @return
     */
    public boolean setValToListCache(String key, E value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将value放入缓存，以 List 形式存储，同时指定过期时间
     *
     * @param key： redis key
     * @param value： value
     * @param expireTime： 有效时间
     * @return
     */
    public boolean setValToListCache(String key, E value, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForList().rightPush(key, value);
                //默认过期时间
                setExpire(key, WEEK_SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForList().rightPush(key, value);
                    //使用指定的过期时间
                    setExpire(key, expireTime);
                } else {
                    //永不过期
                    setSet(key, value);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key： redis key
     * @param value： List value
     * @return
     */
    public boolean setListToListCache(String key, List<E> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存，同时指定过期时间
     *
     * @param key： redis key
     * @param value： List value
     * @param expireTime： 有效时间
     * @return
     */
    public boolean setListToListCache(String key, List<E> value, Long expireTime) {
        try {
            if (expireTime == null) {
                redisTemplate.opsForList().rightPushAll(key, value);
                //默认过期时间
                setExpire(key, WEEK_SECONDS);
            } else {
                if (expireTime > 0) {
                    redisTemplate.opsForList().rightPushAll(key, value);
                    //使用指定的过期时间
                    setExpire(key, expireTime);
                } else {
                    //永不过期
                    setListToListCache(key, value);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取list缓存指定范围的数据
     *
     * @param key： redis key
     * @param start： List start index
     * @param end： List end index
     * @return
     */
    public List<E> getListCache(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key： redis key
     * @return
     */
    public long getListCacheSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取list缓存中指定索引的值
     *
     * @param key： redis key
     * @param index： List Index
     * @return
     */
    public E getListCacheByIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改list中的指定索引处的数据
     *
     * @param key： redis key
     * @param index： List index
     * @param value： 修改后的值
     * @return
     */
    public boolean updateListCacheByIndex(String key, Long index, E value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从list中移除N个值为value的数据
     *
     * @param key： redis key
     * @param count： 需要移除的 List 中的数据的数量
     * @param value： 需要移除的 List 中的数据的值
     * @return
     */
    public long delValFromListCache(String key, Long count, E value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

