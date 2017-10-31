package cn.edu.jxnu.awesome_campus.database.table.about;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyTable {
    /**
     * 通知信息表
     */
    public static final String NAME = "NotifyTable";
    /**
     * 字段部分
     * */
    public static final String NOTIFY_CODE = "NotifyCode";

    public static final String TITLE = "title";

    public static final String TYPE = "type";

    public static final String DATA = "data";

    public static final String READED = "readed";//预留字段，是否已读

    public static final String DATE="date";//预留字段，时间
    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_NOTIFY_CODE = 0;
    public static final int ID_TITLE = 1;
    public static final int ID_TYPE = 2;
    public static final int ID_DATA = 3;
    public static final int ID_READED = 4;
    public static final int ID_DATE = 5;

    public static final String CREATE_TABLE = "create table "+NAME+"("+
            NOTIFY_CODE+" text ,"+
            TITLE+" text, "+
            TYPE+" text, "+
            DATA+" text,"+
            READED+" integer,"+
            DATE+" text)";

    public static final String UPDATE_READED = "update "+NAME+" set "+
            READED+" =?"+" where "+TITLE+" =? ";

}
