package com.github.fashionbrot.tool;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil(){

    }

    /**
     * 日期格式 年 如2009
     */
    public static final String DATE_FORMAT_YEAR = "yyyy";

    /**
     * 日期格式 年 月  如 2009-02
     */
    public static final String DATE_FORMAT_MONTH = "yyyy-MM";

    /**
     * 日期格式 年 月 日 如2009-02-26
     */
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

    /**
     * 日期格式 年 月 日 时 如2009-02-26 15
     */
    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";

    /**
     * 日期格式 年 月 日 时 分 如2009-02-26 15:40
     */
    public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";

    /**
     * 日期格式年 月 日 时 分 秒 如 2009-02-26 15:40:00
     */
    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式年 月 日 时 分 秒 毫秒 如2009-02-26 15:40:00 110
     */
    public static final String DATE_FORMAT_MILLI_SECOND = "yyyy-MM-dd HH:mm:ss SSS";

    public static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_SECOND);

    public static final DateTimeFormatter DATE_FORMAT_DAY_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_DAY);

    /**
     * 格式化 Date 转 String (yyyy-MM-dd HH:mm:ss)
     * @param date
     * @return String
     */
    public static String formatDate( Date date){
        return formatDate(defaultFormatter,date);
    }

    /**
     * 格式化 Date 转 String
     * @param dateTimeFormatter
     * @param date
     * @return String
     */
    public static String formatDate(DateTimeFormatter dateTimeFormatter, Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatter);
    }

    /**
     * 格式化 字符串 转 Date(yyyy-MM-dd HH:mm:ss)
     * @param dataStr
     * @return Date
     */
    public static Date parseDate(String dataStr){
        return parseDate(defaultFormatter,dataStr);
    }

    /**
     * 格式化 字符串 转 Date
     * @param formatter
     * @param dateStr
     * @return Date
     */
    public static Date parseDate(DateTimeFormatter formatter,String dateStr){
        if (StringUtil.isEmpty(dateStr)){
            return null;
        }
        LocalDateTime parse = LocalDateTime.parse(dateStr, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = parse.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 得到本月最后一天的日期
     * @param dateStr
     * @return Date
     */
    public static Date getLastDayOfMonth(String dateStr){
        Date date = parseDate(DATE_FORMAT_DAY_FORMATTER,dateStr);
        if (date==null){
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        return ca.getTime();
    }

    /**
     * 获取日期 年
     * @param date
     * @return int
     */
    public static int getYear(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
