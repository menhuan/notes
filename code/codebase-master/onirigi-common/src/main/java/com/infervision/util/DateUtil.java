package com.infervision.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.*;
import java.util.Date;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
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

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");

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

    /** 
	 * 获取当天上个月的日期
	 * @return: java.util.Date 
	 * @author: fruiqi
	 * @date: 19-5-9 上午11:29
	 */
	public static Date acquireLastMonth() {
		LocalDate now = LocalDate.now();
		LocalDate lastMonth = now.plusMonths(-1);
		return acquireDateByLocalDate(lastMonth);
	}

	/** 
	 *
	 * @param localDate
	 * @return: java.util.Date 
	 * @author: fruiqi
	 * @date: 19-5-9 上午11:29
	 */
	public static Date acquireDateByLocalDate(LocalDate localDate) {
		ZonedDateTime zdt = localDate.atStartOfDay(ZoneId.systemDefault());
		Date date = Date.from(zdt.toInstant());
		return date;
	}

	/** 
	 * 获取时间戳
	 * @param localDate
	 * @return: java.lang.Long 
	 * @author: fruiqi
	 * @date: 19-5-30 下午8:44
	 */
	public static Long acquireTimeByLocalDate(LocalDate localDate) {
		long second = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().getEpochSecond();
		return second;
	}

	/**
	 * 根据localDateTime 获取时间戳
	 * @return: java.lang.Long
	 * @author: fruiqi
	 * @date: 19-5-30 下午8:54
	 */
	public static Long acquireTimeByLocalDateTime(LocalDateTime localDateTime) {
		long second = localDateTime.toEpochSecond(ZoneOffset.of("+8"));
		return second;
	}

	/** 
	 * 获取 指定周 周一时间
	 * num 为正数 负数代表增加月份
	 * @return: java.time.LocalDate 
	 * @author: fruiqi
	 * @date: 19-5-30 下午8:40
	 */
	public static LocalDate acquireWeekMonday(int num) {
		LocalDate localDate = LocalDate.now();
		LocalDate weekMonday = localDate.minusWeeks(num).with(DayOfWeek.MONDAY);
		return weekMonday;
	}

	/**
	 * 获取指定周 周末的时间
	 * num 为正数  负数代表增加月份
	 * @param num
	 * @return: java.time.LocalDate
	 * @author: fruiqi
	 * @date: 19-5-30 下午8:45
	 */
	public static LocalDate acquireWeekSunday(int num) {
		LocalDate localDate = LocalDate.now();
		LocalDate weekSunday = localDate.minusWeeks(num).with(DayOfWeek.SUNDAY);
		return weekSunday;
	}

    /**
	 * 获取当天上个月的日期
	 * @param minusMonth  为正数,如果为负数代表增加月份
	 * @return: java.util.Date
	 * @author: fruiqi
	 * @date: 19-5-9 上午11:29
	 */
	public static Date acquireMonthByMinus(int minusMonth) {
		LocalDate now = LocalDate.now();
		LocalDate lastMonth = now.minusMonths(minusMonth);
		return acquireDateByLocalDate(lastMonth);
	}

	/** 
	 * 将date 格式化为 字符串
	 * @param date
	 * @return: java.lang.String 
	 * @author: fruiqi
	 * @date: 19-5-24 下午12:30
	 */
	public static String formaterDateyyyyMMdd(Date date) {
		String format = yyyyMMdd.format(DateToLocalDate(date));
		return format;
	}

	/** 
	 * 将字符串日期格式化为指定格式
	 * @return: java.util.Date 
	 * @author: fruiqi
	 * @date: 19-6-2 下午3:04
	 */
	public static Date formaterStringDateyyyyMM(String date) {
		LocalDate localDate = LocalDate.parse(date, yyyyMM);
		return acquireDateByLocalDate(localDate);
	}

	/** 
	 * date类型转为localdate
	 * @param date
	 * @return: java.time.LocalDate 
	 * @author: fruiqi
	 * @date: 19-5-24 下午12:29
	 */
	public static LocalDate DateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate localDate = instant.atZone(zoneId).toLocalDate();
		return localDate;
	}

	/** 
	 * 获取初始日期
	 * @return: java.util.Date 
	 * @author: fruiqi
	 * @date: 19-5-31 下午8:30
	 */
	public static Date acquireInitalDate() {
		LocalDate localDate = LocalDate.of(1970, 1, 1);
		return acquireDateByLocalDate(localDate);
	}

	/** 
	 * 获取制定月的1号数据
	 * @return: java.util.Date 
	 * @author: fruiqi
	 * @date: 19-6-2 下午2:56
	 */
	public static Date acquireFirstDayOfMonth(LocalDate localDate, int minusMonth) {
		LocalDate monthLocalDate = localDate.minusMonths(minusMonth);
		LocalDate monthFirstDayOfMonth = monthLocalDate.with(TemporalAdjusters.firstDayOfMonth());
		return acquireDateByLocalDate(monthFirstDayOfMonth);
	}
}
