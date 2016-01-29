package cn.edu.jxnu.awesome_campus.view.base;

import cn.edu.jxnu.awesome_campus.view.IView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface BaseView<V> extends IView<V>{
    void displayLoading();
    void hideLoading();
    void displayNetworkError();
}
