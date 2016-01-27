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
    public static final String CITY_NAME = "city_name";//城市名
    public static final String TEMPERATURE = "temperature";//当前温度
    public static final String INFO = "info";//天气信息
    public static final String DAY_2 = "day_2";//白天温度
    public static final String NIGHT2 = "night_2";//夜间温度
    public static final String DATAUPDATE = "dataUptime";//更新时间
    public static final String DIRECT = "direct";//风向
    public static final String POWER = "power";//风力
    public static final String OFFSET = "offset";//风向偏移量
    public static final String WINDSPEED = "windspeed";//风速
    public static final String CHUANGYI_0 = "chuanyi_0";//穿衣建议短
    public static final String CHUANGYI_1 = "chuangyi_1";//穿衣建议长
    public static final String YUNDONG_0 = "yundong_0";//运动建议短
    public static final String YUNDONG_1 = "yundong_1";//运动建议长
    public static final String GANMAO_0 = "ganmao_0";//感冒指数短
    public static final String GANMAO_1 = "ganmao_1";//感冒指数长
    public static final String ZHIWAIXIAN_0 = "ziwaixian_0";//紫外线指数短
    public static final String ZHIWAIXIAN_1 = "ziwaixian_1";//紫外线指数长
    public static final String WURAN_0 = "wuran_0";//污染指数短
    public static final String WURAN_1 = "wuran_1";//污染指数长

    /**
     * 字段索引
     */
    public static final int ID_CITY_NAME = 0;
    public static final int ID_TEMPERATURE = 1;
    public static final int ID_INFO = 2;
    public static final int ID_DAY_2 = 3;
    public static final int ID_NIGHT_2 = 4;
    public static final int ID_DATAUPDATE = 5;
    public static final int ID_DIRECT = 6;
    public static final int ID_POWER = 7;
    public static final int ID_OFFSET = 8;
    public static final int ID_WINDSPEED = 9;
    public static final int ID_CHUANGYI_0 = 10;
    public static final int ID_CHUANGYI_1 = 11;
    public static final int ID_YUNDONG_0 = 12;
    public static final int ID_YUNDONG_1 = 13;
    public static final int ID_GANMAO_0 = 14;
    public static final int ID_GANMAO_1 = 15;
    public static final int ID_ZHIWAIXIAN_0 = 16;
    public static final int ID_ZHIWAIXIAN_1 = 17;
    public static final int ID_WURAN_0 = 18;
    public static final int ID_WURAN_1 = 19;

    /**
     * 建表
     */
    public static final String CREATE_TABLE = "create table " + NAME + "(" +
            CITY_NAME + " text," +
            TEMPERATURE + " text, " +
            INFO + " text, " +
            DAY_2 + " text, " +
            NIGHT2 + " text, " +
            DATAUPDATE + " text, " +
            DIRECT + " text, " +
            POWER + " text, " +
            OFFSET + " text, " +
            WINDSPEED + " text, " +
            CHUANGYI_0 + " text, " +
            CHUANGYI_1 + " text, " +
            YUNDONG_0 + " text, " +
            YUNDONG_1 + " text, " +
            GANMAO_0 + " text, " +
            GANMAO_1 + " text, " +
            ZHIWAIXIAN_0 + " text, " +
            ZHIWAIXIAN_1 + " text, " +
            WURAN_0 + " text, " +
            WURAN_1 + " text)";

}
