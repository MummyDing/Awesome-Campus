package cn.edu.jxnu.awesome_campus.view.base;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public interface BaseListView extends BaseView<BaseListView> {
    boolean trySetupRefreshLayout();
    void onDataRefresh();
    void bindAdapter();
    void addHeader();
}
