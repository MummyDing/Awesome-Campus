package cn.edu.jxnu.awesome_campus.ui.base;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.view.base.BaseListView;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseListFragment extends BaseFragment implements BaseListView {
    @Override
    protected void init() {
        initView();
    }


    @Override
    protected int getLayoutID() {
        return R.layout.layout_common_list;
    }

    @Override
    public boolean trySetupRefreshLayout() {
        return false;
    }

    @Override
    public boolean trySetupSmartTabLayout() {
        return false;
    }

    @Override
    public void displayLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void displayNetworkError() {

    }


}
