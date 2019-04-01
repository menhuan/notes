package com.infervision.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @ClassName fruiqi
 * @Description 日期处理类
 * @Author frq
 * @Date 2019/3/31 0:30
 * @Version 1.0
 */
public class DateUtil {

    public static final DateTimeFormatter yyyyMMddHHmmssff = DateTimeFormatter.ofPattern("%Y-%m-%d %H:%M:%S.%f");
    /**
     * @Author fruiqi
     * @Description  日期进行指定处理
     * @Date 0:32 2019/3/31
     * @Param [date]
     * @return java.lang.String
     **/
    public static String dateToString(Date date){
        long time = date.getTime() - 1L;
        Date date1 = new Date();
        date1.setTime(time);
        LocalDateTime localDateTime = dateToLocalDateTime(date1);
        String format = localDateTime.format(yyyyMMddHHmmssff);
        return format;
    }


    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant,zoneId);
        return localDateTime;
    }

    public static String stringToString(String date){
        LocalDateTime parse = LocalDateTime.parse(date);
        long l = parse.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() - 1;
        LocalDateTime localDateTime = timeToLocalDateTime(l);
        String format = localDateTime.format(yyyyMMddHHmmssff);
        return format;
    }

    public static LocalDateTime timeToLocalDateTime(Long time){
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(time / 1000, 0, ZoneOffset.ofHours(8));
        return localDateTime;
    }
}
