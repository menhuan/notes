package com.infervision.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infervision.model.ProfessorContent;
import com.infervision.service.ProfessorService;
import com.infervision.service.WordService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/27 23:36
 * @Version 1.0
 */
@RestController
public class ProfessorControler {


    private static final Logger logger = LoggerFactory.getLogger(ProfessorControler.class);

    @Autowired
    ProfessorService professorService;

    @Autowired
    WordService wordService;

    @GetMapping("start")
    public List<Map> start(String url) {
        List<Map> per = this.per(url);
        if (per == null){
            logger.error(url);
            return per;
        }
        List<ProfessorContent> professorContents = professorService.acquireContent(per);
//        professorService.toExcel(professorContents);
        wordService.toWordByContent(professorContents);
        return per;
    }

    public List<Map> per(String url){
        logger.info("[INFO] read message");
        if (url == null){
            url = "https://api.zsxq.com/v1.10/groups/222454121411/topics?scope=by_owner&count=20";
        }
        //问答 https://api.zsxq.com/v1.10/groups/222454121411/topics?scope=by_owner&count=20。
        JSONObject jsonObject = professorService.professorService(url);
        Map resp_date =(Map) jsonObject.get("resp_data");
        Object topics = resp_date.get("topics");

        List<Map> maps = JSON.parseArray(JSON.toJSONString(topics), Map.class);
        if (maps.size()==20){
            String create_time = (String) maps.get(19).get("create_time");
            String substring = StringUtils.substring(create_time, create_time.length() - 8, create_time.length() - 5);
            Integer i = Integer.parseInt(substring) - 1;
            String cont = i.toString();
            if (i<100){
                cont = "0" + cont;
            }
            String join = StringUtils.join(StringUtils.substring(create_time, 0, create_time.length() - 8), cont,"+0800");
            try {
//                String s = URLEncoder.encode(join, "UTF-8").toLowerCase();
//                String replace = StringUtils.replace(s, "t", "T", 1);
                url = "https://api.zsxq.com/v1.10/groups/222454121411/topics?scope=by_owner&count=20" +"&end_time="+join;
                List<Map> start = this.start(url);
                if (start == null){
                    return maps;
                }
                maps.addAll(start);
            } catch (Exception e) {
                logger.error("[ERROR] 转移错误",e);

            }
        }else {
            return maps;
        }

        return maps;
    }


}

