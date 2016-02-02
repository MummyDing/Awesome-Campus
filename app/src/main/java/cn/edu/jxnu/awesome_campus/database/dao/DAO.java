package cn.edu.jxnu.awesome_campus.database.dao;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface DAO<M> {
    boolean cacheAll(List<M> list);
    boolean clearCache();
    void loadFromCache(List<M> list);
    void loadFromNet(List<M> list);
}
