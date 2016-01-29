package cn.edu.jxnu.awesome_campus.presenter.home;

import cn.edu.jxnu.awesome_campus.model.home.DrawerItemModel;
import cn.edu.jxnu.awesome_campus.view.home.HomeView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView view;
    private DrawerItemModel model;

    public HomePresenterImpl(HomeView view) {
        this.view = view;
        model = new DrawerItemModel();
    }

    @Override
    public void setModel(DrawerItemModel drawerItemModel) {
        model = drawerItemModel;
    }

    @Override
    public DrawerItemModel getModel() {
        return model;
    }

    @Override
    public void setView(HomeView homeView) {
        view = homeView;
    }

    @Override
    public HomeView getView() {
        return view;
    }

    @Override
    public void initlization() {
        view.initView();
    }
}
