package cn.edu.jxnu.awesome_campus.database.table.library;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchTable {
    /**
     * 图书热搜缓存表
     */

    public static final String NAME = "HotSearchTable";

    public static final String TAG = "tag";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_TAG = 0;


    public static final String CREATE_TIME = "create table "+NAME+"("+
            TAG+" text)";

}
