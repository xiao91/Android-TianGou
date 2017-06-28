package com.quanmin.sky.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 获取不同定义的时间类型
 *
 * @author xiao 2017-05-23
 */
public class DateUtils {

    // 1分钟
    private static final long minute = 60 * 1000;
    // 1小时
    private static final long hour = 60 * minute;
    // 1天
    private static final long day = 24 * hour;
    // 月
    private static final long month = 31 * day;
    // 年
    private static final long year = 12 * month;

    // 日期格式
    private static final String TEMPLATE_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间
     *
     * @param template 日期格式
     * @return date 日期
     */
    public static String getCurrentDate(String template) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(template, Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }

    /**
     * unix时间转北京时间
     *
     * @param unixTime unix时间
     * @return date 时间
     */
    public static String getBeiJingDate(long unixTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TEMPLATE_DEFAULT, Locale.CHINA);
        return simpleDateFormat.format(new Date(unixTime * 1000));
    }

    /**
     * 北京时间转unix时间
     *
     * @param bjTime 标准北京时间
     * @return time long型值
     */
    public static long getUnixTime(String bjTime) {
        long time = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TEMPLATE_DEFAULT, Locale.CHINA);
        try {
            Date date = simpleDateFormat.parse(bjTime);
            time = date.getTime() / 1000;
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将日期格式化成计算出的字符串：几分钟前、几小时前、几天前、几月前、几年前、刚刚
     *
     * @param milliseconds 毫秒时间戳
     * @return date 日期提示
     */
    public static String formatDate(long milliseconds) {
        Date date = new Date(milliseconds * 1000);
        long diff = new Date().getTime() - date.getTime();
        long r;
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
