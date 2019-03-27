package com.infervision.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.infervision.util.Constants.USER_AGENT_ARRAP;

/**
 * @ClassName fruiqi
 * @Description request 工具包
 * @Author frq
 * @Date 2019/3/27 22:26
 * @Version 1.0
 */
public class RequestUtil {


    /**
     * @return java.lang.String
     * @Author fruiqi
     * @Description 爬取知识星球的rest
     * @Date 22:30 2019/3/27
     * @Param []
     **/
    public String restStar(String token, String cookie) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", USER_AGENT_ARRAP);
        headers.set("zsxq_access_token", token);
        headers.set("Referer", "https://wx.zsxq.com/dweb/");
        headers.set("cookie", cookie);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> exchange = restTemplate.exchange("https://api.zsxq.com/v1.10/groups/721455121/topics?scope=digests&count=20",
                HttpMethod.GET, entity, String.class);
        String result = exchange.getBody();
        return result;
    }

    /**
     * @return java.lang.String
     * @Author fruiqi
     * @Description 爬虫设置header, 访问的url
     * @Date 22:53 2019/3/27
     * @Param [headMap, url]
     **/
    public String restStar(Map<String, String> headMap, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headMap.forEach((k, v) -> {
            headers.set(k, v);
        });
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url,
                HttpMethod.GET, entity, String.class);
        String result = exchange.getBody();
        return result;
    }
}
