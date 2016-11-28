package com.doubook.utiltools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeCompare {
    public static int turnTime(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
        return Integer.parseInt(st.format(time));
    }

    public static String turnTime1(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHHmm");
        return st.format(time);
    }

    public static String turnTime2(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return st.format(time);
    }

    public static String turnTime3(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("HHmmss");
        return st.format(time);
    }

    public static String turnTime4(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
        return st.format(time);
    }

    public static String turnTime5(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("yyyyMMdd");
        return st.format(time);
    }


    public static int turnTimeHours(Date time) {
        SimpleDateFormat st = new SimpleDateFormat("HH");
        return Integer.parseInt(st.format(time));
    }

    public static Date StringToDate(String s) {
        Date time = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            time = sd.parse(s);
        } catch (ParseException e) {
            System.out.println("error::" + s);
        }
        return time;
    }

    public static Date StringToDateTime2(String s) {
        Date time = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        try {
            time = sd.parse(s);
        } catch (ParseException e) {
            System.out.println("error::" + s);
        }
        return time;
    }

    public static void main(String[] args) {
        String noticeid = "2015-06-01";
        String noticeid1 = "2015-05-01";
        System.out.println(turnTime3(new Date()));
    }
}
