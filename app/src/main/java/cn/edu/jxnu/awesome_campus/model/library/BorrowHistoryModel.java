package cn.edu.jxnu.awesome_campus.model.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.library.BorrowHistoryDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BorrowHistoryModel  implements IModel<BorrowHistoryModel> {

    private BorrowHistoryDAO borrowHistoryDAO;
    /***
    * 借阅历史缓存表
    */

    // 图书条形号
    private String BookCode;
    private String BookTitle;
    private String Author;
    // 借阅日期
    private String BorrowTime;
    // 归还日期
    private String BackTime;
    // 馆藏地
    private String BookLocation;

    public BorrowHistoryModel() {
        borrowHistoryDAO = new BorrowHistoryDAO();
    }

    public BorrowHistoryModel(String bookCode, String bookTitle, String author, String borrowTime, String backTime, String bookLocation) {
        this();
        BookCode = bookCode;
        BookTitle = bookTitle;
        Author = author;
        BorrowTime = borrowTime;
        BackTime = backTime;
        BookLocation = bookLocation;
    }

    @Override
    public void loadFromCache() {
        borrowHistoryDAO.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        borrowHistoryDAO.loadFromNet();
    }

    @Override
    public boolean clearCache() {
        return borrowHistoryDAO.clearCache();
    }

    @Override
    public boolean cacheAll(List<BorrowHistoryModel> list) {
        return borrowHistoryDAO.cacheAll(list);
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getBorrowTime() {
        return BorrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        BorrowTime = borrowTime;
    }

    public String getBackTime() {
        return BackTime;
    }

    public void setBackTime(String backTime) {
        BackTime = backTime;
    }

    public String getBookLocation() {
        return BookLocation;
    }

    public void setBookLocation(String bookLocation) {
        BookLocation = bookLocation;
    }
}
