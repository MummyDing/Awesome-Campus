package cn.edu.jxnu.awesome_campus.support.utils.common;

import android.support.v4.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;

/**
 * 格式化获取时间
 * Created by KevinWu on 2016/2/5.
 */
public class TimeUtil {
    private static Calendar calendar;
    private static Date date;

    private static void setTime() {
        calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
    }

    /**
     * 返回格式示例：1454664319
     *
     * @return Long Timestamp
     * @author KevinWu
     * create at 2016/2/5 17:24
     */
    public static long getTimestamp() {
        setTime();
        long time = 0;
        time = date.getTime();
        return time;
    }

    /**
     * 返回格式示例：16-01-12
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 17:39
     */
    public static String getYearMonthDay() {
        setTime();
        String year = "";
        String month = "";
        String day = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        month = df.format(calendar.get(Calendar.MONTH));
        day = df.format(calendar.get(Calendar.DAY_OF_MONTH) + 1);
        return year + "-" + month + "-" + day;
    }

    /**
     * 返回格式示例：15
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 18:11
     */
    public static String getYear_xx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
     * 返回格式示例：2015
     *
     * @return Formated String Time
     * @author KevinWu
     * create at 2016/2/5 20:53
     */
    public static String getYear_xxxx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("0000");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }

    /**
    *返回格式示例：05
    *@author KevinWu
    *create at 2016/2/5 20:55
    */
    public static String getMonth(){
        setTime();
        String month= "";
        DecimalFormat df = new DecimalFormat("00");
        month = df.format(calendar.get(Calendar.MONTH));
        return month;
    }

    /**
     * 取得当前时间在一个月中的第几号
    *返回格式示例：19
    *@author KevinWu
    *create at 2016/2/5 20:58
    */
    public static String getDay(){
        setTime();
        String day="";
        DecimalFormat df=new DecimalFormat("00");
        day=df.format(calendar.get(Calendar.DAY_OF_MONTH)+1);
        return  day;
    }

    /**
    *取得当前完整时间，精确到分
     * 返回格式示例：2015-12-08 19:30
    *@author KevinWu
    *create at 2016/2/5 20:59
    */
    public static String getFullTime(){
        setTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime=dateFormat.format(date);
        return nowTime;
    }

    /**
    *取得当前是周几，返回整型，周一到周日依次对应是1~7
    *@author KevinWu
    *create at 2016/2/5 21:17
    */
    public static int getDayOfWeek(){
        setTime();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0)dayOfWeek=7;
        return dayOfWeek;
    }

    /**
     * 返回当前星期几的字符串
     * @return
     */
    public static String getWeekString(){
        String time=null;
        setTime();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0)dayOfWeek=7;
        switch (dayOfWeek){
            case 1:
                time= InitApp.AppContext.getResources().getString(R.string.monday);
                break;
            case 2:
                time= InitApp.AppContext.getResources().getString(R.string.tuesday);
                break;
            case 3:
                time= InitApp.AppContext.getResources().getString(R.string.wednesday);
                break;
            case 4:
                time= InitApp.AppContext.getResources().getString(R.string.tuesday);
                break;
            case 5:
                time= InitApp.AppContext.getResources().getString(R.string.friday);
                break;
            case 6:
                time= InitApp.AppContext.getResources().getString(R.string.saturday);
                break;
            case 7:
                time= InitApp.AppContext.getResources().getString(R.string.sunday);
                break;
        }
        return time;
    }

    public static int getHour(){
        setTime();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public static String getCourseArea(int i){
        String time = null;
        switch (i) {
            case 0:
                time = "08:00~09:30";
                break;
            case 1:
                time = "09:40~10:20";
                break;
            case 2:
                time = "10:30~11:10";
                break;
            case 3:
                time = "11:20~12:00";
                break;
            case 4:
                time = "14:00~15:30";
                break;
            case 5:
                time = "15:40~17:10";
                break;
            case 6:
                time = "19:00~20:30";
                break;
        }
        return time;
    }
}
