package com.xiao91.sky.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 获取不同定义的时间类型
 * Created by XL on 2016.08.23.
 */
public class DateUtils {

    // 1分钟
    private final static long minute = 60 * 1000;
    // 1小时
    private final static long hour = 60 * minute;
    // 1天
    private final static long day = 24 * hour;
    // 月
    private final static long month = 31 * day;
    // 年
    private final static long year = 12 * month;

    /**
     * 时间戳
     *
     * @param template 日期类型
     * @return date
     */
    public String getDate(String template) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(template, Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }

    /**
     * unix时间转北京时间
     *
     * @param unixDate
     * @return
     */
    public static String getBeiJingDate(long unixDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return simpleDateFormat.format(new Date(unixDate * 1000));
    }

    /**
     * 北京时间转unix时间
     *
     * @param strDate
     * @return
     */
    public static long getUnixDate(String strDate) {
        long time = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        try {
            Date date = simpleDateFormat.parse(strDate);
            time = date.getTime() / 1000;
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将日期格式化成友好的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param milliseconds 时间戳
     * @return
     */
    public static String formatFriendly(long milliseconds) {
        Date date = new Date(milliseconds * 1000);
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }

}
