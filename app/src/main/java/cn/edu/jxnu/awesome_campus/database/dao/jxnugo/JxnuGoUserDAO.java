package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserModel;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnuGoUserDAO implements DAO<JxnuGoUserModel> {
    @Override
    public boolean cacheAll(List<JxnuGoUserModel> list) {
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
        //这里写用户信息网络请求
    }
}
