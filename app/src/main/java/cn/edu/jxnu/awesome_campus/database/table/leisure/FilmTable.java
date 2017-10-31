package cn.edu.jxnu.awesome_campus.database.table.leisure;

/**
 * Created by MummyDing on 16-4-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmTable {
    /***
     * 电影
     */
    public static final String NAME = "FilmTable";
    /***
     * 字段部分
     */
    public static final String URL = "url";

    public static final String TITLE = "title";

    public static final String READING_COUNT = "readingCount";

    public static final String DETAIL = "detail";

    public static final String TOP_PIC = "topPic";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_URL = 0;

    public static final int ID_TITLE = 1;

    public static final int ID_READING_COUNT = 2;

    public static final int ID_DETAIL = 3;

    public static final int ID_TOP_PIC = 4;

    public static final String CREATE_TABLE = "create table "+NAME+"("+
            URL+" text, "+
            TITLE+" text primary key, "+
            READING_COUNT+" text, "+
            DETAIL+" text, "+
            TOP_PIC+" text)";


}
