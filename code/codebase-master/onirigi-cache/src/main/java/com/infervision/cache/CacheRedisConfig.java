package com.infervision.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Map;

/**
 * @author: fruiqi
 * @date: 19-6-14 上午11:22
 * @version:1.0
 **/
@Configuration
@EnableCaching
@ConditionalOnProperty(name = "spring.cache.redis.enabled", havingValue = "true")
@EnableConfigurationProperties(CacheProperties.class)
public class CacheRedisConfig {
    
    /** 
     * key 也是用json序列化，字符串形式注入时存在类型转换。
     * @return: void 
     * @author: fruiqi
     * @date: 19-6-14 上午11:37
     */ 
    @Autowired
    public void setDefaultRedisSerializer(RedisTemplate<Object, Object> redisTemplate) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();
    }
    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    public CacheManagerCustomizer<RedisCacheManager> redisCacheManagerCustomizer() {
        return cacheManager -> {
            Map<String, Long> expires = cacheProperties.getKeyAndExpires();
            cacheManager.setCacheNames(expires.keySet());
            cacheManager.setExpires(expires);
        };
    }

}
