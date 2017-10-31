package cn.edu.jxnu.awesome_campus.database.table.home;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsTable {
    /***
     * 校内新闻信息表
     */
    public static final String NAME = "CampusNewsTable";

    /**
     * 字段部分
     * 主键: 新闻标题 NewsTitle
     */
    public static final String NEWS_TITLE = "NewsTitle";
    // 新闻发布时间
    public static final String NEWS_TIME = "NewsTime";
    public static final String NEWS_URL = "NewsURL";
    // 标题图链接
    public static final String NEWS_PIC_URL = "NewsPicURL";
    // 新闻拉取到本地的时间
    public static final String UPDATE_TIME = "UpdateTime";

    // 新闻详情
    public static final String NEWS_DETAILS = "NewsDetails";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_NEWS_TITLE = 0;
    public static final int ID_NEWS_TIME = 1;
    public static final int ID_NEWS_URL = 2;
    public static final int ID_NEWS_PIC_URL = 3;
    public static final int ID_UPDATE_TIME = 4;
    public static final int ID_NEWS_DETAILS = 5;



    public static final String CREATE_TABLE = "create table "+NAME+"("+
            NEWS_TITLE+" text primary key, "+
            NEWS_TIME+" text, "+
            NEWS_URL+" text, "+
            NEWS_PIC_URL+" text, "+
            UPDATE_TIME+" text, "+
            NEWS_DETAILS+" text)";

    public static final String UPDATE_DETAILS = "update "+NAME+" set "+NEWS_DETAILS
            +"= ?"+" where "+NEWS_TITLE+" =? ";

}
