package cn.edu.jxnu.awesome_campus.database.table.life;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class WeatherInfoTable {
    /***
     * 天气信息表
     */
    public static final String NAME = "WeatherInfoTable";

    /**
     * 当天气源返回的数据中有对应的名称时采用该名称命名字段，当没有对应名称时以"数组名_对应下标"命名
     */
    //日期
    public static final String DATE = "date";
    //白天天气

    //天气ID
    public static final String DAY_ID = "dayID";

    // 天气
    public static final String DAY_WEA = "daWea";

    //高温
    public static final String DAY_TMP = "dayTmp";

    //风向
    public static final String DAY_WIND_DIR = "dayWindDir";

    //风力
    public static final String DAY_WIND_POWER = "dayWindPower";

    //更新时间
    public static final String DAY_TIME = "dayTime";

    //夜间

    //天气ID
    public static final String NIGHT_ID = "nightID";

    // 天气
    public static final String NIGHT_WEA = "nightWea";
    //低温
    public static final String NIGHT_TMP = "nightTmp";

    //风向
    public static final String NIGHT_WIND_DIR = "nightWindDir";

    //风力
    public static final String NIGHT_WIND_POWER = "nightWindPower";

    //更新时间
    public static final String NIGHT_TIME = "nightTime";

    /**
     * 字段索引
     */

    public static final int ID_DATE = 0;
    public static final int ID_DAY_ID = 1;
    public static final int ID_DAY_WEA = 2;
    public static final int ID_DAY_TMP = 3;
    public static final int ID_DAY_WIND_DIR = 4;
    public static final int ID_DAY_WIND_POWER = 5;
    public static final int ID_DAY_TIME = 6;

    public static final int ID_NIGHT_ID = 7;
    public static final int ID_NIGHT_WEA = 8;
    public static final int ID_NIGHT_TMP = 9;
    public static final int ID_NIGHT_WIND_DIR = 10;
    public static final int ID_NIGHT_WIND_POWER = 11;
    public static final int ID_NIGHT_TIME = 12;
    /**
     * 建表
     */
    public static final String CREATE_TABLE = "create table " + NAME + "(" +
            DATE + " text," +
            DAY_ID + " text, " +
            DAY_WEA + " text, " +
            DAY_TMP + " text, " +
            DAY_WIND_DIR + " text, " +
            DAY_WIND_POWER + " text, " +
            DAY_TIME + " text, " +
            NIGHT_ID + " text, " +
            NIGHT_WEA+ " text, "+
            NIGHT_TMP + " text, " +
            NIGHT_WIND_DIR + " text, " +
            NIGHT_WIND_POWER + " text, " +
            NIGHT_TIME + " text)";

}
