package com.infervision.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午2:30
 * @version:1.0 日期 工具类
 **/
public class DateUtil {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 日期格式
     */
    public static final DateTimeFormatter ddmmyyyy = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /**
     * 倒叙日期值
     * @author fruiqi
     * @date 19-2-11 下午6:03
     * @param
     * @return java.lang.String
     **/
    public static String getDDMMYYYY(){
        LocalDate localDate = LocalDate.now();
        String result = localDate.format(ddmmyyyy);
        return result;

    }


}
