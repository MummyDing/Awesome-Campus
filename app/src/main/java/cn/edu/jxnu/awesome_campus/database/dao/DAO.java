package cn.edu.jxnu.awesome_campus.database.dao;

import java.util.List;


/**
 * Created by MummyDing on 16-2-2.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface DAO<M> {
    /**
     * 将数据写入到数据库中
     * @param list  数据源 需要写入的数据
     * @return
     */
    boolean cacheAll(List<M> list);

    /**
     * 清除缓存
     * @return
     */
    boolean clearCache();


    /***
     * 从数据库中获取数据
     * @param list 如果获取到数据则添加到list中(添加前先clear)   失败则发送失败消息(EventBus)
     */
    void loadFromCache(List<M> list);

    /**
     * 从网络拉取数据
     * @param list 如果拉取成功则将拉取的数据添加到list中(添加前先clear)  失败则发送失败消息(EventBus)
     */
    void loadFromNet(List<M> list);
}
