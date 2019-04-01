package com.infervision.service;

import com.alibaba.fastjson.JSONObject;
import com.infervision.model.ProfessorContent;

import java.util.List;
import java.util.Map;

/**
 * @ClassName fruiqi
 * @Description 教授星球爬虫service
 * @Author frq
 * @Date 2019/3/27 22:56
 * @Version 1.0
 */
public interface ProfessorService {


    /**
     * @Author fruiqi
     * @Description  教授星球爬虫
     * @Date 22:57 2019/3/27
     * @Param []
     * @return void
     **/
    public JSONObject professorService(String url);
    
    /**
     * @Author fruiqi
     * @Description  获取问答内容
     * @Date 23:29 2019/4/1
     * @Param [maps]
     * @return java.util.List<com.infervision.model.ProfessorContent>
     **/
    public List<ProfessorContent> acquireContent(List<Map> maps);

    /**
     * @Author fruiqi
     * @Description  写入内容到excel
     * @Date 23:39 2019/4/1
     * @Param [cons]
     * @return void
     **/
    public void toExcel(List<ProfessorContent> cons);
}
