package cn.edu.jxnu.awesome_campus.view.home;

import cn.edu.jxnu.awesome_campus.view.IView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface HomeView extends IView<HomeView> {
    void setTitle(String title);
    void switchDrawerItem(int id);
    void switchToLogin();
}
