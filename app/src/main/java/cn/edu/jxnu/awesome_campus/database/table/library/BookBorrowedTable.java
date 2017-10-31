package cn.edu.jxnu.awesome_campus.database.table.library;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookBorrowedTable {

    /**
     * 已借图书缓存表
     */
    public static final String NAME = "BookBorrowedTable";

    // 图书条形号
    public static final String BOOK_CODE = "BookCode";
    public static final String BOOK_TITLE = "BookTitle";
    public static final String AUTHOR = "Author";
    // 借书日期
    public static final String BORROW_TIME = "BorrowTime";
    //
    // 应还日期
    public static final String SHOULD_BACK_TIME = "ShouldBackTime";
    // 续借次数
    public static final String AGAIN_TIMES = "AgainTimes";
    // 馆藏地
    public static final String BOOK_LOCATION = "BookLocation";


    public static final int ID_BOOK_CODE = 0;
    public static final int ID_BOOK_TITLE = 1;
    public static final int ID_AUTHOR = 2;
    public static final int ID_BORROW_TIME = 3;
    public static final int ID_SHOULD_BACK_TIME = 4;
    public static final int ID_AGAIN_TIMES = 5;
    public static final int ID_BOOK_LOCATION = 6;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            BOOK_CODE+" text, "+
            BOOK_TITLE+" text, "+
            AUTHOR+" text, "+
            BORROW_TIME+" text, "+
            SHOULD_BACK_TIME+" text, "+
            AGAIN_TIMES+" text, "+
            BOOK_LOCATION+" text)";


    public static final String UPDATE_RENEW = "update "+NAME+" set "+
            AGAIN_TIMES+" =1"+" where "+BOOK_CODE+" =?";
}
