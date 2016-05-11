package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnugoUserDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnugoUserModel implements IModel<JxnugoUserModel> {

    private static final String TAG="JxnugoUserModel";
    private JxnugoUserDAO dao;
    private String about_me;
    private String last_seen;
    private String location;
    private String menber_since;
    private String name;
    private int postCount;
    private String sex;
    private String userName;

    private String collectionPost;
    private String followed;
    private String followers;



    @Override
    public boolean cacheAll(List<JxnugoUserModel> list) {
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
}
