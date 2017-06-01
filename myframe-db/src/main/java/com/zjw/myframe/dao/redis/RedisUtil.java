package com.zjw.myframe.dao.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * Redis封装方法
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年7月28日
 */
@Repository
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> stringRedisTemplate;

    private Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 自减1
     *
     * @param key 键
     */
    public Long decr(String key){
        return decrBy(key, 1L);
    }

    /**
     * 自减
     *
     * @param key   键
     * @param value 值
     */
    public Long decrBy(final String key, final long value){
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            try{
                return connection.decrBy(key.getBytes(), value);
            }catch(InvalidDataAccessApiUsageException e){
                log.error("值不为数值型，无法自减", e);
                return null;
            }
        });
    }

    /**
     * 删除
     *
     * @param key 键
     */
    public void delete(final String key){
        stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            connection.del(key.getBytes());
            return true;
        });
    }

    /**
     * 是否存在
     *
     * @param key 键
     */
    public boolean exists(final String key){
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> connection.exists(key.getBytes()));
    }

    /**
     * 更新超时
     *
     * @param key  键
     * @param time 过期时间（S）为0则为永久不过期
     */
    public boolean expire(final String key, final long time){
        return stringRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            if(time == 0){
                return connection.persist(key.getBytes());
            }else{
                return connection.expire(key.getBytes(), time);
            }
        });
    }

    /**
     * 获取
     *
     * @param key 键
     */
    public String get(final String key){
        return stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            byte[] bytes = connection.get(key.getBytes());
            if(bytes == null){
                return null;
            }
            return new String(bytes);
        });
    }

    /**
     * 获取
     *
     * @param key 键
     * @param t   目标对象
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key, T t){
        String result = stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            byte[] bytes = connection.get(key.getBytes());
            if(bytes == null){
                return null;
            }
            return new String(bytes);
        });
        try{
            if(t instanceof String){
                return (T) result;
            }
            t = (T) JSON.parseObject(result, t.getClass());
        }catch(Exception e){
            log.error("JSON构造对象错误：result:" + result + ">>>>" + t.getClass(), e);
            return null;
        }

        return t;
    }

    /**
     * 自增1
     *
     * @param key 键
     */
    public Long incr(String key){
        return incrBy(key, 1L);
    }

    /**
     * 自增
     *
     * @param key   键
     * @param value 值
     */
    public Long incrBy(final String key, final long value){
        return stringRedisTemplate.execute((RedisCallback<Long>) connection -> {
            try{
                return connection.incrBy(key.getBytes(), value);
            }catch(InvalidDataAccessApiUsageException e){
                log.error("值不为数值型，无法自增", e);
                return null;
            }
        });
    }

    /**
     * 添加
     *
     * @param key   键
     * @param value 值
     */
    public String set(String key, Object value){
        return set(key, value, 0);
    }

    /**
     * 添加
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间（S）为0则为永久不过期
     */
    public String set(final String key, final Object value, final long time){
        return stringRedisTemplate.execute((RedisCallback<String>) connection -> {
            String valueStr = value.toString();
            if(!(value instanceof String)){
                valueStr = JSON.toJSONString(value);
            }
            if(time == 0){
                connection.set(key.getBytes(), valueStr.getBytes());
            }else{
                connection.setEx(key.getBytes(), time, valueStr.getBytes());
            }
            return valueStr;
        });
    }

}
