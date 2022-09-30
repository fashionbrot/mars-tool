package com.github.fashionbrot.tool.date;

import com.github.fashionbrot.tool.ObjectUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DateUtil {

    private DateUtil(){

    }


    public static final String DATE_FORMAT_YEAR = "yyyy";
    public static final String DATE_FORMAT_MONTH = "yyyy-MM";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_MILLI_SECOND = "yyyy-MM-dd HH:mm:ss SSS";

    public static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_SECOND);

    public static final DateTimeFormatter DATE_FORMAT_DAY_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT_DAY);

    /**
     * 格式化Date to String (yyyy-MM-dd HH:mm:ss)
     * @param date
     * @return String
     */
    public static String format( Date date){
        return formatDate(defaultFormatter,date);
    }

    /**
     * 格式化Date to String
     * @param dateTimeFormatter
     * @param date
     * @return String
     */
    public static String formatDate(DateTimeFormatter dateTimeFormatter, Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(dateTimeFormatter);
    }

    /**
     * 格式化字符串 to Date(yyyy-MM-dd HH:mm:ss)
     * @param dataStr
     * @return Date
     */
    public static Date parseDate(String dataStr){
        return parseDate(defaultFormatter,dataStr);
    }

    /**
     * 格式化字符串 to Date
     * @param formatter
     * @param dateStr
     * @return Date
     */
    public static Date parseDate(DateTimeFormatter formatter,String dateStr){
        if (ObjectUtil.isEmpty(dateStr)){
            return null;
        }
        LocalDateTime parse = LocalDateTime.parse(dateStr, formatter);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = parse.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 获取本月最后一天
     * @param dateStr
     * @return
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


    public static Date formatDate(Date date){
        Calendar c= Calendar.getInstance();
        c.setTime(date);
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date formatLongDate(Long date){
        Calendar c= Calendar.getInstance();
        c.setTime(new Date(date));
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    public static Date formatTime(Date date){
        Calendar c= Calendar.getInstance();
        c.setTime(date);
        //年
        c.set(Calendar.YEAR,1970);
        //月
        c.set(Calendar.MONTH,0);
        //日
        c.set(Calendar.DAY_OF_MONTH,1);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date formatDate(Date date,int day){
        Calendar c= Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);

        return c.getTime();
    }

    public static Date addDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }


    public static Date addDay(Date date,int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    public static Date addDay(Date date,int calendar,int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendar, day);
        return c.getTime();
    }

    public static Date setDateHourOfDay(Date date,int hour){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    public static Calendar addDate(Calendar c,int day){
        c.add(Calendar.DAY_OF_MONTH, day);
        return c;
    }

    public static Calendar getCalendar(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static Date setCalendar(Date date,int calendar,int calendarValue){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendar,calendarValue);
        return c.getTime();
    }

    public static int getCalendar(Date date,int calendar){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(calendar);
    }

    public static Date isZeroAndDay(Date date,int day){
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        if(hour==0 && minute==0 && second==0){
            c.add(Calendar.DAY_OF_MONTH, day);
        }
        return c.getTime();
    }

    public static LinkedHashMap<Date,String> getReadSheetList(String startDateStr, String endDateStr, SimpleDateFormat sf){
        if (sf==null){
            sf =new SimpleDateFormat("yyyy-MM-dd");
        }
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sf.parse(startDateStr);
            endDate = sf.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getReadSheetList(startDate,endDate,sf);
    }

    public static LinkedHashMap<Date,String> getReadSheetList(Date startDate,Date endDate,SimpleDateFormat sf) {

        LinkedHashMap<Date,String> readSheetCountList=new LinkedHashMap<>();
        if (startDate.getTime()==endDate.getTime() || startDate.getTime()>endDate.getTime()){
            readSheetCountList.put(startDate,sf.format(startDate.getTime()));
            return readSheetCountList;
        }
        Calendar day = null;
        for(;;){
            if (day==null){
                day = getCalendar(startDate);
            }else{
                day = addDate(day,1);
            }
            readSheetCountList.put(day.getTime(),sf.format(day.getTime()));
            if (day.getTime().getTime()==endDate.getTime()){
                break;
            }
        }
        return readSheetCountList;
    }


    public static List<Date> getDateList(Date startDate, Date endDate) {

        List<Date> list=new ArrayList<>();
        if (startDate.getTime()==endDate.getTime() || startDate.getTime()>endDate.getTime()){
            list.add(startDate);
            return list;
        }
        Calendar day = null;
        for(;;){
            if (day==null){
                day = getCalendar(startDate);
            }else{
                day = addDate(day,1);
            }
            list.add(day.getTime());
            if (day.getTime().getTime()==endDate.getTime()){
                break;
            }
        }
        return list;
    }


    /**
     * 获取当前月第一天
     * @return
     */
    public static Date getFirstDayOfMonth(Date now) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        // 时
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        calendar.set(Calendar.MINUTE, 0);
        // 秒
        calendar.set(Calendar.SECOND, 0);
        // 毫秒
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    public static Date getMonthFirstDay(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    public static Date getMonthLastDay(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    public static Date getMonthLastDay(Date date){
        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH,-1);
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 时
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 分
        c.set(Calendar.MINUTE, 0);
        // 秒
        c.set(Calendar.SECOND, 0);
        // 毫秒
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

}
