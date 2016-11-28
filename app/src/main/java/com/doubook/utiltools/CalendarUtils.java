package com.doubook.utiltools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by MaryLee on 2016/10/4.
 */

public class CalendarUtils {
    public static int getDaySplite(long specialDate) {
        Calendar calendar2 = Calendar.getInstance();
        long nowDate = calendar2.getTime().getTime(); //Date.getTime() 获得毫秒型日期
        //计算间隔多少天，则除以毫秒到天的转换公式
        return (int) (nowDate-specialDate) / (1000 * 60 * 60 * 24);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = "2016-10-01 11:11:11";
        long specialDate = sdf.parse(dateString).getTime();
        System.out.println(System.currentTimeMillis());
        System.out.println(specialDate);


        System.out.println(getDaySplite(specialDate));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = "2016-01-01 11:11:11";
//        Calendar calendar2 = Calendar.getInstance();
//        long nowDate = calendar2.getTime().getTime(); //Date.getTime() 获得毫秒型日期
//        long specialDate = calendar.getTime().getTime();
//        //计算间隔多少天，则除以毫秒到天的转换公式
//        long betweenDate = (specialDate - nowDate) / (1000 * 60 * 60 * 24);
//        System.out.print(betweenDate);
    }
}
