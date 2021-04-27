package com.infervision.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.infervision.util.ConstantUtil.EMPTY;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午2:58
 * @version:1.0 redis 操作类
 **/
@Component
public class RedisOperation {

    private static final Logger looger = LoggerFactory.getLogger(RedisOperation.class);

    /**
     * 日志组件
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisOperation.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /**
     * @param key 键值
     * @return java.lang.String
     * @author fruiqi
     * @description //TODO 得到指定的value
     * @date 18-10-22 下午2:57
     **/
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param key   指定的key
     * @param value 对应的value
     * @author fruiqi
     * @description //TODO 给制定的key设置值
     * @date 18-10-22 下午3:00
     **/
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }


    /**
     *  该方法是让取出List中的全部数据，不适合大批量在list里面全部数据取出
     * @param key 对应的key
     * @return java.util.List<java.lang.String>
     * @author fruiqi
     * @description //TODO 取出来 对应的key 集合数据 ,操作List的数据少可以采用该方案
     * @date 18-10-22 下午3:06
     **/
    public List<String> popList(String key) {
        Long end = redisTemplate.opsForList().size(key);
        List<String> range = redisTemplate.opsForList().range(key, 0, end);
        return range;
    }


    /**
     * @return java.lang.Long
     * @author fruiqi
     * @description //TODO 插入集合数据
     * @date 18-10-22 下午3:09
     **/
    public Long pushList(String key, List<String> obs) {
        Long value = redisTemplate.opsForList().leftPushAll(key, obs);
        return value;
    }

    /**
     * @return java.lang.String
     * @author fruiqi
     * @description //TODO 得到 集合中的值
     * @date 18-10-22 下午3:16
     **/
    public Set<String> getMembers(String key) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        return members;
    }

    /**
     * @return void
     * @author fruiqi
     * @description //TODO 插入集合数据
     * @date 18-10-22 下午3:30
     **/
    public void setMember(String key, String[] value) {
        try {
            redisTemplate.opsForSet().add(key, value);
        } catch (Exception e) {
            logger.error("[ERROR] set collection error :{} value :{}", key, value);
        }
    }

    /**
     * @return void
     * @author fruiqi
     * @description //TODO 插入hash值
     * @date 18-10-22 下午3:41
     **/
    public void setHashValue(String key, Map<String, String> value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    /**
     * //TODO 得到对应的值
     *
     * @param key
     * @param hashKey
     * @return java.lang.String
     * @author fruiqi
     * @date 19-1-17 下午4:27
     **/
    public String getHashValue(String key, String hashKey) {
        Object object = redisTemplate.opsForHash().get(key, hashKey);
        String result = EMPTY;
        if (object != null) {
            result = object.toString();
        }
        return result;
    }

    /**
     * //TODO 插入数据到hash中
     *
     * @param key     item的key
     * @param hashKey 项目的key
     * @param value   对应的value
     * @return void
     * @author fruiqi
     * @date 19-1-17 下午4:55
     **/
    public void addHashValue(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量增加 对象
     *
     * @param key    key值
     * @param values
     * @return void
     * @author fruiqi
     * @date 19-1-29 下午2:17
     **/
    public void addHashValues(String key, Map<String, String> values) {
        redisTemplate.opsForHash().putAll(key, values);
    }
}
