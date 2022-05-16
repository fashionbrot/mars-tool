package com.github.fashionbrot.tool.date;

import com.github.fashionbrot.tool.ObjectUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatUtil {


    private static final ThreadLocal<SimpleDateFormat> df = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            return df;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> dfDateTime = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return df;
        }
    };

    private static final ThreadLocal<SimpleDateFormat> dfTime = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

            return df;
        }
    };

    /**
     *
     * @param time HH:mm:ss
     * @return
     */
    public static Date parseTime(String time){
        if (ObjectUtil.isEmpty(time)){
            return null;
        }
        try {
            return dfTime.get().parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param date HH:mm:ss
     * @return
     */
    public static String formatTime(Date date){
        if (date==null){
            return "";
        }
        return dfTime.get().format(date);
    }


    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateTime(Date date){
        if (date==null){
            return "";
        }
        return dfDateTime.get().format(date);
    }

    /**
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return Date
     */
    public static Date parseDateTime(String dateStr){
        if (ObjectUtil.isEmpty(dateStr)){
            return null;
        }
        try {
            return dfDateTime.get().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 格式化 Date
     * @param date
     * @return String(yyyy-MM-dd)
     */
    public static String format(Date date){
        if (date==null){
            return "";
        }
        return df.get().format(date);
    }


    /**
     * parse(yyyy-MM-dd) 字符串 为Date
     * @param dateStr
     * @return Date
     */
    public static Date parse(String dateStr){
        if (ObjectUtil.isEmpty(dateStr)){
            return null;
        }
        try {
            return df.get().parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
