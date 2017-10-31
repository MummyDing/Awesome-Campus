package cn.edu.jxnu.awesome_campus.database.table.library;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchHistoryTable {

    /***
     * 检索历史缓存表
     * 说明: 注销需清空此表 否则新账号检索历史将发生冲突
     */
    public static final String NAME = "BookSearchHistoryTable";

    // 检索时间
    public static final String SEARCH_TIME = "SearchTime";
    // 检索条件
    public static final String SEARCH_CONDITION = "SearchCondition";
    // 检索关键字
    public static final String SEARCH_KEYWORD = "SearchKeyword";



    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_SEARCH_TIME = 0;
    public static final int ID_SEARCH_CONDITION = 1;
    public static final int ID_SEARCH_KEYWORD = 2;

    public static final String CREATE_TABLE ="create table "+NAME+"("+
            SEARCH_TIME+" text, "+
            SEARCH_CONDITION+" text, "+
            SEARCH_KEYWORD+" text)";
}
