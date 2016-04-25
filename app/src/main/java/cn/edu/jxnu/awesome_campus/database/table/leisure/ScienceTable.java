package cn.edu.jxnu.awesome_campus.database.table.leisure;

/**
 * Created by MummyDing on 16-4-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceTable {
    /***
     * 科学
     */
    public static final String NAME = "ScienceTable";

    /***
     * 字段部分
     */
    public static final String REPLIES_COUNT = "RepliesCount";

    public static final String IMAGE_URL = "ImageUrl";

    public static final String URL = "url";

    public static final String TITLE = "title";

    public static final String SCIENCE_DETAILS = "ScienceDetails";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_REPLIES_COUNT = 0;

    public static final int ID_IMAGE_URL = 1;

    public static final int ID_URL = 2;

    public static final int ID_TITLE = 3;

    public static final int ID_SCIENCE_DETAILS = 4;

    public static final String CREATE_TABLE = "create table "+NAME+"("+
            REPLIES_COUNT+" integer, "+
            IMAGE_URL+" text, "+
            URL+" text, "+
            TITLE+" text primary key, "+
            SCIENCE_DETAILS+" text)";


    public static final String UPDATE_DETAILS = "update "+NAME+" set "+
            SCIENCE_DETAILS+" = ?"+" where "+TITLE+" = ?";


}
