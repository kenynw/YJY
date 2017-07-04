package com.miguan.yjy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Copyright (c) 2017/2/28. LiaoPeiKun Inc. All rights reserved.
 */

public class DateUtils {

    public static String getCurrentFormatDate() {
        return getCurrentFormatDate("yyyy-MM-dd");
    }

    public static String getCurrentFormatDate(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getFormatTime(long time) {
        long hours = ((time % (1000 * 3600 * 24)) / (1000 * 3600));
        long minutes = ((time % (1000 * 3600)) / (1000 * 60));
        long seconds = (time % (1000 * 60) / 1000);
        return String.format("%1$02d:%2$02d:%3$02d", hours, minutes, seconds);
    }

    public static String getFormatDay(long time) {
        long days = (time / (1000 * 3600 * 24));
        long hours = ((time % (1000 * 3600 * 24)) / (1000 * 3600));
        long minutes = ((time % (1000 * 3600)) / (1000 * 60));
        long seconds = (time % (1000 * 60) / 1000);
        return String.format("%1$2d:%2$2d:%3$2d:%4$2d", days, hours, minutes, seconds);
    }

    /**
     * 日期转换成字符串
     *
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String str = format.format(date);
        return str;
    }

    //字符串转时间戳
    public static String getTime(String timeString){
        return getTime(timeString, "yyyy-MM-dd");
    }

    //字符串转时间戳
    public static String getTime(String timeString, String pattern){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime() / 1000;
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

}
