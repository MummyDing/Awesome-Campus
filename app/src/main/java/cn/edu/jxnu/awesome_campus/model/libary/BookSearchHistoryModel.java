package cn.edu.jxnu.awesome_campus.model.libary;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchHistoryModel implements IModel<BookSearchHistoryModel> {

    /***
     * 检索历史缓存表
     */
    // 检索时间
    private String SearchTime;
    // 检索条件
    private String SearchCondition;
    // 检索关键字
    private String SearchKeyword;

    public BookSearchHistoryModel() {
    }

    public BookSearchHistoryModel(String searchTime, String searchCondition, String searchKeyword) {
        SearchTime = searchTime;
        SearchCondition = searchCondition;
        SearchKeyword = searchKeyword;
    }


    @Override
    public List<BookSearchHistoryModel> loadFromNet() {
        return null;
    }

    @Override
    public List<BookSearchHistoryModel> loadFromCache() {
        return null;
    }

    @Override
    public boolean cleanCache() {
        return false;
    }

    public String getSearchTime() {
        return SearchTime;
    }

    public void setSearchTime(String searchTime) {
        SearchTime = searchTime;
    }

    public String getSearchCondition() {
        return SearchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        SearchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return SearchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        SearchKeyword = searchKeyword;
    }
}
