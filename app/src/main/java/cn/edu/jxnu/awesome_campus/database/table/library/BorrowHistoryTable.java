package cn.edu.jxnu.awesome_campus.database.table.library;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BorrowHistoryTable {
    /***
     * 借阅历史缓存表
     */
    public static final String NAME = "BorrowHistoryTable";

    // 图书条形号
    public static final String BOOKCODE = "BookCode";
    public static final String BOOKTITLE = "BookTitle";
    public static final String AUTHOR = "Author";
    // 借阅日期
    public static final String BORROW_TIME = "BorrowTime";
    // 归还日期
    public static final String BACK_TIME = "BackTime";
    // 馆藏地
    public static final String BOOK_LOCATION = "BookLocation";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_BOOKCODE = 0;
    public static final int ID_BOOKTITLE = 1;
    public static final int ID_AUTHOR = 2;
    public static final int ID_BORROW_TIME = 3;
    public static final int ID_BACK_TIME = 4;
    public static final int ID_BOOK_LOCATION = 5;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            BOOKCODE+" text, "+
            BOOKTITLE+" text, "+
            AUTHOR+" text, "+
            BORROW_TIME+" text, "+
            BACK_TIME+" text, "+
            BOOK_LOCATION+" text)";
}
