package cn.edu.jxnu.awesome_campus.model.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.library.BookSearchResultDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-19.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchResultModel implements IModel<BookSearchResultModel> {

    private BookSearchResultDAO dao;
    private String bookTitle;
    private String bookNumber;
    private String bookClass;
    private String bookAuthor;
    private String bookPublisher;
    // 可借
    private String bookLeft;
    // 馆藏
    private String bookCount;


    private String url;


    public BookSearchResultModel(String keyword) {
        dao = new BookSearchResultDAO(keyword);
    }

    public BookSearchResultModel(String bookTitle, String bookNumber, String bookClass, String bookAuthor, String bookPublisher, String bookLeft, String bookCount, String url) {
        this("");
        this.dao = dao;
        this.bookTitle = bookTitle;
        this.bookNumber = bookNumber;
        this.bookClass = bookClass;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookLeft = bookLeft;
        this.bookCount = bookCount;
        this.url = url;
    }

    @Override
    public boolean cacheAll(List<BookSearchResultModel> list) {
        return dao.cacheAll(list);
    }

    @Override
    public boolean clearCache() {
        return dao.clearCache();
    }

    @Override
    public void loadFromCache() {
        dao.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dao.loadFromNet();
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getBookClass() {
        return bookClass;
    }

    public void setBookClass(String bookClass) {
        this.bookClass = bookClass;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookLeft() {
        return bookLeft;
    }

    public void setBookLeft(String bookLeft) {
        this.bookLeft = bookLeft;
    }

    public String getBookCount() {
        return bookCount;
    }

    public void setBookCount(String bookCount) {
        this.bookCount = bookCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
