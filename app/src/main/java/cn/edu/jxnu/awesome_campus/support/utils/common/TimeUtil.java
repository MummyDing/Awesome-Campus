package cn.edu.jxnu.awesome_campus.support.utils.common;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 格式化获取时间
 * Created by KevinWu on 2016/2/5.
 */
public class TimeUtil {
    private static Calendar calendar;
    private static Date date;

    private static void setTime(){
        calendar=Calendar.getInstance();
        date=new Date();
        calendar.setTime(date);
    }

    /**
     * 返回格式示例：1454664319
    *@return Long Timestamp
    *@author KevinWu
    *create at 2016/2/5 17:24
    */
    public static long getTimestamp(){
        setTime();
        long time=0;
        time=date.getTime();
        return time;
    }

    /**
     * 返回格式示例：16-01-12
    *@return Formated String Time
    *@author KevinWu
    *create at 2016/2/5 17:39
    */
    public static String getYearMonthDay(){
        setTime();
        String year="";
        String month="";
        String day="";
        DecimalFormat df=new DecimalFormat("00");
        year=df.format(calendar.get(Calendar.YEAR));
        month=df.format(calendar.get(Calendar.MONTH));
        day=df.format(calendar.get(Calendar.DAY_OF_MONTH)+1);
        return year+"-"+month+"-"+day;
    }

    /**
    *@return Formated String Time
    *@author KevinWu
    *create at 2016/2/5 18:11
    */
    public static String getYear_xx(){
        setTime();
        String year="";
        DecimalFormat df=new DecimalFormat("00");
        year=df.format(calendar.get(Calendar.YEAR));
        return year;
    }
}
