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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        return str;
    }

    //字符串转时间戳
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }



}
