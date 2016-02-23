package cn.edu.jxnu.awesome_campus.database.table.home;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTable {
    /**
     * 课程表
     */
    public static final String NAME = "CourseTable";

    /***
     * 字段部分
     * 不设主键
     *  说明: 注销需清空此表 否则新账号登陆课程表信息将发生冲突
     */
    // 礼拜几 DayOfWeek
    public static final String DAY_OF_WEEK = "DayOfWeek";
    // 第几学期
    public static final String TERM = "Term";
    // 每节课的课程信息
    // 课程名+地点 中间用@分割  然后再根据课程名可以到课程信息表中查询具体课程信息
    public static final String ONE_TWO = "OneTwo";
    public static final String THREE = "Three";
    public static final String FOUR = "Four";
    public static final String FIVE = "Five";
    public static final String SIX_SEVEN = "SixSeven";
    public static final String EIGHT_NINE = "EightNine";
    // 晚上的课
    public static final String NIGHT = "Night";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_DAY_OF_WEEK = 0;
    public static final int ID_TERM = 1;
    public static final int ID_ONE_TWO = 2;
    public static final int ID_THREE = 3;
    public static final int ID_FOUR = 4;
    public static final int ID_FIVE = 5;
    public static final int ID_SIX_SEVEN = 6;
    public static final int ID_EIGHT_NINE = 7;
    public static final int ID_NIGHT = 8;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            DAY_OF_WEEK +" integer,"+
            TERM+" text, "+
            ONE_TWO+" text, "+
            THREE+" text, "+
            FOUR+" text, "+
            FIVE+" text, "+
            SIX_SEVEN+" text, "+
            EIGHT_NINE+" text, "+
            NIGHT+" text)";


}
