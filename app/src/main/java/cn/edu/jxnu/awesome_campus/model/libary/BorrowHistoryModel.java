package cn.edu.jxnu.awesome_campus.model.libary;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BorrowHistoryModel implements IModel<BorrowHistoryModel> {

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
    }

    public BorrowHistoryModel(String bookCode, String bookTitle, String author, String borrowTime, String backTime, String bookLocation) {
        BookCode = bookCode;
        BookTitle = bookTitle;
        Author = author;
        BorrowTime = borrowTime;
        BackTime = backTime;
        BookLocation = bookLocation;
    }


    @Override
    public void loadFromNet() {
    }

    @Override
    public void loadFromCache() {
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public boolean cacheAll() {
        return false;
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
