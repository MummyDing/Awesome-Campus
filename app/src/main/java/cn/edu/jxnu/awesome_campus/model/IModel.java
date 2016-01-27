package cn.edu.jxnu.awesome_campus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MummyDing on 16-1-27.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface IModel<T extends IModel> extends Serializable{
    /**
     * 从网络拉取数据
     * @return
     */
    List<T> loadFromNet();

    /**
     * 从数据库获取数据
     * @return
     */
    List<T> loadFromCache();

    /***
     * 清除缓存
     * @return
     */
    boolean cleanCache();

    /***
     * 写入缓存
     * @return
     */
    boolean cache();

}
