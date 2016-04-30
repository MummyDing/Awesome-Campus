package cn.edu.jxnu.awesome_campus.presenter.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;

import cn.edu.jxnu.awesome_campus.presenter.IPresenter;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface HomePresenter extends IPresenter{
    void buildDrawer(Activity activity, Toolbar toolbar);
    void buildHeader(Activity activity,String avatarURL,String studentID,String name);
    void updateHeader(Activity activity);
    void clearAllFragments();
    boolean isDrawerOpen();
    void closeDrawer();
    int getCurrentSelectedID();
    void updateSelectedToHome();
}
