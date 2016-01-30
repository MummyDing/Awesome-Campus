package cn.edu.jxnu.awesome_campus.presenter.home;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.DrawerItem;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.view.home.HomeView;

/**
 * Created by MummyDing on 16-1-29.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HomePresenterImpl implements HomePresenter {

    private HomeView homeView;
    private AccountHeader header;
    private Drawer drawer;

    public HomePresenterImpl(HomeView view) {
        this.homeView = view;
    }



    @Override
    public void initlization() {
        homeView.initView();
    }

    @Override
    public void buildDrawer(Activity activity,Toolbar toolbar) {
        header = new AccountHeaderBuilder().withActivity(activity)
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                        .withName(activity.getString(R.string.hint_click_to_login)))
                .withHeaderBackground(R.drawable.header)
                .build();
        drawer = new DrawerBuilder().withActivity(activity).withAccountHeader(header)
                .withToolbar(toolbar).withActionBarDrawerToggleAnimated(true).addDrawerItems(
                buildPrimaryItem(DrawerItem.HOME.getItemName(),DrawerItem.HOME.getItemIconID(),DrawerItem.HOME.getId()),
                buildPrimaryItem(DrawerItem.LEISURE.getItemName(),DrawerItem.LEISURE.getItemIconID(),DrawerItem.LEISURE.getId()),
                buildPrimaryItem(DrawerItem.LIFE.getItemName(),DrawerItem.LIFE.getItemIconID(),DrawerItem.LIFE.getId()),
                buildPrimaryItem(DrawerItem.STUDY.getItemName(),DrawerItem.STUDY.getItemIconID(),DrawerItem.STUDY.getId()),
                buildPrimaryItem(DrawerItem.LIBRARY.getItemName(),DrawerItem.LIBRARY.getItemIconID(),DrawerItem.LIBRARY.getId()),
                buildPrimaryItem(DrawerItem.EDUCATION.getItemName(),DrawerItem.EDUCATION.getItemIconID(),DrawerItem.EDUCATION.getId()),
                new DividerDrawerItem(),
                        buildSecondaryItem(DrawerItem.THEME.getItemName(),DrawerItem.THEME.getItemIconID(),DrawerItem.THEME.getId()),
                        buildSecondaryItem(DrawerItem.SETTINGS.getItemName(),DrawerItem.SETTINGS.getItemIconID(),DrawerItem.SETTINGS.getId()),
                        buildSecondaryItem(DrawerItem.LOGOUT.getItemName(),DrawerItem.LOGOUT.getItemIconID(),DrawerItem.LOGOUT.getId())
               ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        homeView.switchDrawerItem(drawerItem.getIdentifier());
                        return false;
                    }
                })
                .build();
        homeView.setTitle(activity.getString(R.string.home));

    }

    @Override
    public void buildHeader(Activity activity, String avatarURL,String studentID, String name) {
        if(drawer != null){
            drawer.removeHeader();
        }
        if(header == null){
            header = new AccountHeaderBuilder().withActivity(activity).build();
        }else {
            header.clear();
        }
        if(TextUtil.isNull(studentID) || TextUtil.isNull(name) || TextUtil.isNull(avatarURL)){
            header.addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                    .withName(activity.getString(R.string.hint_click_to_login)));
        }
    }


    private PrimaryDrawerItem buildPrimaryItem(String name,int icon,int id){
        return new PrimaryDrawerItem().withName(name).withIcon(icon).withIdentifier(id);
    }
    private SecondaryDrawerItem buildSecondaryItem(String name,int icon,int id){
        return new SecondaryDrawerItem().withName(name).withIcon(icon).withIdentifier(id);
    }
    private PrimaryDrawerItem buildPrimaryItem(String name,int id){
        return new PrimaryDrawerItem().withName(name).withIdentifier(id);
    }
    private SecondaryDrawerItem buildSecondaryItem(String name,int id){
        return new SecondaryDrawerItem().withName(name).withIdentifier(id);
    }

}
