package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentModel;

/**
 * Created by KevinWu on 16-5-13.
 */
public class CommentDAO implements DAO<CommentModel> {
    @Override
    public boolean cacheAll(List<CommentModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {
        loadFromNet();
    }

    @Override
    public void loadFromNet() {

    }
}
