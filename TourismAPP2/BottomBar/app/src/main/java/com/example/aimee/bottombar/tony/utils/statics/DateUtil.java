package com.example.aimee.bottombar.tony.utils.statics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TonyJiang on 2016/3/26.
 */
public class DateUtil {
    private static final DateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //返回当前时间格式化String类型表示
    public static String GetDateString(){
        Date date_now = new Date();
        String str_date_now = sdf.format(date_now);
        return str_date_now;
    }
    //返回当前时间Date类型
    public static Date getDate(){
        return new Date();
    }
    /*
     * 从String转换成Date类
     */
    public static Date GetDateFromString(String str_date){
        Date date = null;
        try {
            date = dft.parse(str_date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    private static int TimeBetween(Date date1,Date date2){
        int time = (int)((date1.getTime()-date2.getTime())>0?(date1.getTime()-date2.getTime())
                :(date2.getTime()-date1.getTime()));
        return time;
    }

    public static int DayBetween(String date_str1,String date_str2){
        try {
            Date date1 = dft.parse(date_str1);
            Date date2 = dft.parse(date_str2);
            int day = TimeBetween(date1, date2);
            day/=24*60*60*1000;
            return day;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int DayBetween(Date date1,Date date2){
        int day = TimeBetween(date1, date2);
        day/=24*60*60*1000;
        return day;
    }
    public static int HourBetween(String str1,String str2){
        try {
            Date date1 = dft.parse(str1);
            Date date2 = dft.parse(str2);
            int hour = TimeBetween(date1, date2);
            hour/=60*60*1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    public static int HourBetween(Date date1,Date date2){
        int hour = TimeBetween(date1, date2);
        hour/=60*60*1000;
        return hour;
    }
    public static int MinuteBetween(String str1,String str2){

        try {
            Date date1 = dft.parse(str1);
            Date date2 = dft.parse(str2);
            int minute = TimeBetween(date1,date2);
            minute /=1000*60;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;

    }
    public static int MinuteBetween(Date date1,Date date2){
        int minute = TimeBetween(date1, date2);
        minute/=60*1000;
        return minute;
    }
}
