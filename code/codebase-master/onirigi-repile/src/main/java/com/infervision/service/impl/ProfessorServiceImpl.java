package com.infervision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.infervision.model.ProfessorContent;
import com.infervision.service.ProfessorService;
import com.infervision.util.RequestUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/27 23:10
 * @Version 1.0
 */
@Service
public class ProfessorServiceImpl implements ProfessorService {

    private static final Logger logger = LoggerFactory.getLogger(ProfessorServiceImpl.class);

    /**
     * @return void
     * @Author fruiqi
     * @Description 去对应的url进行下载
     * @Date 23:10 2019/3/27
     * @Param [url]
     **/
    @Override
    public JSONObject professorService(String url) {
        Map<String, String> headMap = new HashMap<>(10);
        headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0");
        headMap.put("Referer", "https://wx.zsxq.com/dweb/");
        headMap.put("cookie", "zsxq_access_token=5B226373-F7CD-2A89-19E4-E83CE82F1C19");
        RequestUtil requestUtil = new RequestUtil();
        String res = requestUtil.restStar(headMap, url);
        JSONObject jsonObject = JSON.parseObject(res);
        return jsonObject;

    }

    @Override
    public List<ProfessorContent> acquireContent(List<Map> maps) {
        List<ProfessorContent> collect = maps.parallelStream().map(content -> {
            ProfessorContent con = new ProfessorContent();
            Map talk = (Map) content.get("talk");
            if (talk == null || talk.size() == 0) {
                Map questionMap = (Map) content.get("question");
                String question = (String) questionMap.get("text");
                con.setQuestionee(question);

                Map answerMap = (Map) content.get("answer");
                String answer = (String) answerMap.get("text");
                con.setAnswer(answer);
            } else {

                if (talk.get("text")!=null){
                    con.setQuestionee(" ");
                    con.setAnswer(talk.get("text").toString());
                }

            }

            return con;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void toExcel(List<ProfessorContent> cons) {
        //初始化csvformat
        CSVFormat formator = CSVFormat.DEFAULT.withRecordSeparator("\n");

        //创建FileWriter对象
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("f:/VSCODE\\new.csv");
            //创建CSVPrinter对象
            CSVPrinter printer = new CSVPrinter(fileWriter, formator);
            String[] head = new String[2];
            head[0] = "question";
            head[1] = "answer";
            printer.printRecord(head);
            if (cons != null) {
                for (ProfessorContent professorContent : cons) {
                    printer.printRecord();
                    printer.printRecord(
                            "    " + professorContent.getQuestionee()
                            , "    " + professorContent.getAnswer());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
