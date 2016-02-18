package cn.edu.jxnu.awesome_campus.database.dao.library;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;

/**
 * Created by MummyDing on 16-2-19.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchResultDAO implements DAO<BookSearchResultModel> {

    public static final String TAG = "BookSearchResultDAO";

    @Override
    public boolean cacheAll(List<BookSearchResultModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }
}
