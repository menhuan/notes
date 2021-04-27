package com.infervision.cache;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: fruiqi
 * @date: 19-6-14 上午11:13
 * @version:1.0
 **/
@ConfigurationProperties(prefix = "spring.cache.redis")
public class CacheProperties {

    private ConcurrentHashMap keyAndExpires;

    public ConcurrentHashMap getKeyAndExpires() {
        return keyAndExpires;
    }

    /**
     * 从配置文件中读取参数 将其内容注入到map中
     *
     * @return: void
     * @author: fruiqi
     * @date: 19-6-14 下午2:35
     */
    public void setKeyAndExpires(String expires) {
        keyAndExpires = new ConcurrentHashMap();
        String[] split = StringUtils.split(expires, ",");
        for (String keyAndValue : split) {
            String[] values = StringUtils.split(keyAndValue, ":");
            keyAndExpires.put(values[0].trim(), Long.parseLong(values[1].trim()));
        }
    }
}
