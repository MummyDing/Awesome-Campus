package cn.edu.jxnu.awesome_campus.database.table.leisure;

/**
 * Created by MummyDing on 16-2-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyTable {
    /**
     * 日报
     */
    public static final String NAME = "DailyTable";
    /**
     * 字段部分
     */
    public static final String TITLE = "title";

    public static final String ID = "id";

    public static final String BODY = "body";

    public static final String LARGE_PIC = "largePic";

    public static final String ITEM_PIC = "itemPic";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_TITLE = 0;
    public static final int ID_ID = 1;
    public static final int ID_BODY = 2;
    public static final int ID_LARGE_PIC = 3;
    public static final int ID_ITEM_PIC = 4;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            TITLE+" text primary key, "+
            ID+" integer, "+
            BODY + " text, "+
            LARGE_PIC + " text, "+
            ITEM_PIC + " text)";


    public static final String UPDATE_DETAILS = "update "+NAME+" set "+
            BODY+" =? "+" and "+LARGE_PIC+"=?"+" where "+TITLE+" = ?";
}
