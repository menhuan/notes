package com.infervision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infervision.service.ProfessorService;
import com.infervision.util.RequestUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.infervision.util.Constants.USER_AGENT_ARRAP;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/27 23:10
 * @Version 1.0
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {
    
    
    /**
     * @Author fruiqi
     * @Description  去对应的url进行下载
     * @Date 23:10 2019/3/27
     * @Param [url]
     * @return void
     **/
    @Override
    public void ProfessorService(String url) {
        Map<String,String> headMap = new HashMap<>(10);
        headMap.put("User-Agent",USER_AGENT_ARRAP);
        headMap.put("Referer","https://wx.zsxq.com/dweb/");
        headMap.put("cookie","zsxq_access_token=CD063C9D-9A81-B150-C996-35B20D2E1ABD");
        RequestUtil requestUtil = new RequestUtil();
        String res = requestUtil.restStar(headMap, url);
        JSONObject jsonObject = JSON.parseObject(res);
    }
}
