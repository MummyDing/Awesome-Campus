package cn.edu.jxnu.awesome_campus.presenter.home;

import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.api.AvatarApi;
import cn.edu.jxnu.awesome_campus.model.common.DrawerItem;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.education.EducationFragment;
import cn.edu.jxnu.awesome_campus.ui.home.HomeFragment;
import cn.edu.jxnu.awesome_campus.ui.job.JobHomeFragment;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.JxnugoFragment;
import cn.edu.jxnu.awesome_campus.ui.leisure.LeisureFragment;
import cn.edu.jxnu.awesome_campus.ui.library.LibraryFragment;
import cn.edu.jxnu.awesome_campus.ui.life.LifeFragment;
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

    private SecondaryDrawerItem LogItem;


    public HomePresenterImpl(HomeView view) {
        this.homeView = view;
    }



    @Override
    public void initlization() {
        homeView.initView();
    }



    @Override
    public void buildDrawer(Activity activity,Toolbar toolbar) {

        LogItem = buildSecondaryItem(DrawerItem.LOGOUT.getItemName(),DrawerItem.LOGOUT.getItemIconID(),DrawerItem.LOGOUT.getId());
        if(header == null){
            updateHeader(activity);
        }
        drawer = new DrawerBuilder().withActivity(activity).withAccountHeader(header)
                .withToolbar(toolbar).withActionBarDrawerToggleAnimated(true).addDrawerItems(
                        buildPrimaryItem(DrawerItem.HOME.getItemName(),DrawerItem.HOME.getItemIconID(),DrawerItem.HOME.getId()),
                        buildPrimaryItem(DrawerItem.LIBRARY.getItemName(),DrawerItem.LIBRARY.getItemIconID(),DrawerItem.LIBRARY.getId()),
                        buildPrimaryItem(DrawerItem.JOB.getItemName(),DrawerItem.JOB.getItemIconID(),DrawerItem.JOB.getId()),
                        buildPrimaryItem(DrawerItem.LEISURE.getItemName(),DrawerItem.LEISURE.getItemIconID(),DrawerItem.LEISURE.getId()),
                        buildPrimaryItem(DrawerItem.LIFE.getItemName(),DrawerItem.LIFE.getItemIconID(),DrawerItem.LIFE.getId()),
                        buildPrimaryItem(DrawerItem.JXNUGO.getItemName(),DrawerItem.JXNUGO.getItemIconID(),DrawerItem.JXNUGO.getId()),
                        buildPrimaryItem(DrawerItem.EDUCATION.getItemName(),DrawerItem.EDUCATION.getItemIconID(),DrawerItem.EDUCATION.getId()),
                        new DividerDrawerItem(),
                        buildSecondaryItem(DrawerItem.THEME.getItemName(),DrawerItem.THEME.getItemIconID(),DrawerItem.THEME.getId()),
                        buildSecondaryItem(DrawerItem.SETTINGS.getItemName(),DrawerItem.SETTINGS.getItemIconID(),DrawerItem.SETTINGS.getId()),
                        buildSecondaryItem(DrawerItem.ABOUT.getItemName(),DrawerItem.ABOUT.getItemIconID(),DrawerItem.ABOUT.getId()),
                        buildSecondaryItem(DrawerItem.LOGOUT.getItemName(),DrawerItem.LOGOUT.getItemIconID(),DrawerItem.LOGOUT.getId())
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        homeView.switchDrawerItem(drawerItem.getIdentifier());
                        return false;
                    }
                }).build();
        homeView.setTitle(activity.getString(R.string.home));
        updateHeader(activity);


    }


    public void updateHeader(Activity activity){
        EducationLoginUtil.isLogin();
        if(JxnuGoLoginUtil.isLogin()&&Settings.avatorID == 1){
            Log.d("--","使用jxnugo头像");
            if(EducationLoginUtil.isLogin())
            buildHeader(activity, Settings.avatorID == 1 ? JxnuGoLoginUtil.getUserAvatar(): AvatarApi.baseAvatarUrl+(Settings.avatorID-1)+".png",EducationLoginUtil.getStudentID(),EducationLoginUtil.getStudentName());
            else{
                buildHeader(activity, Settings.avatorID == 1 ? JxnuGoLoginUtil.getUserAvatar(): AvatarApi.baseAvatarUrl+(Settings.avatorID-1)+".png","--","JxnuGo: "+JxnuGoLoginUtil.getUserName());
            }
        }else{
            buildHeader(activity, Settings.avatorID == 0 ? EducationLoginUtil.getAvatorUrl(): AvatarApi.baseAvatarUrl+(Settings.avatorID-1)+".png",EducationLoginUtil.getStudentID(),EducationLoginUtil.getStudentName());
        }
    }


    @Override
    public void buildHeader(Activity activity, String avatarURL,String studentID, String name) {
        if(header == null){
            header = new AccountHeaderBuilder().withActivity(activity)
                    .withHeaderBackground(R.drawable.headerpic)
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                            homeView.switchToLogin();
                            return false;
                        }
                    })
                    .build();
        }else {
            header.clear();
        }
//        if(TextUtil.isNull(studentID) || TextUtil.isNull(name) || TextUtil.isNull(avatarURL)){
//            header.addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
//                    .withName(activity.getString(R.string.hint_click_to_login)));
//        }
        if(TextUtil.isNull(studentID) || TextUtil.isNull(name) || TextUtil.isNull(avatarURL)){
            header.addProfiles(new ProfileDrawerItem().withIcon(R.drawable.logo)
                    .withName(activity.getString(R.string.hint_click_to_login)));
        }
        else{
            header.addProfiles(new ProfileDrawerItem().withIcon(avatarURL)
                    .withName(name.length() == 2 ?"    "+name :"  "+name));
        }

        if(EducationLoginUtil.isLogin() || LibraryLoginUtil.isLogin()|| JxnuGoLoginUtil.isLogin()){
            Log.d("--","有一个账号登录了");
            if(drawer !=null) {
                Log.d("","");
                LogItem.withEnabled(true);
                LogItem.withName(DrawerItem.LOGOUT.getItemName());
                LogItem.withIcon(DrawerItem.LOGOUT.getItemIconID());
                drawer.updateItem(LogItem);
            }
        }else{
            if(drawer !=null) {
                LogItem.withEnabled(false);
                LogItem.withName("");
                LogItem.withIcon(R.drawable.ic_blank);
                drawer.updateItem(LogItem);
            }
        }

        header.getHeaderBackgroundView().setBackgroundColor(ThemeConfig.themeColor[Config.themeSelected]);
    }

    public boolean isDrawerOpen(){
        return drawer.isDrawerOpen();
    }

    public void closeDrawer(){
        drawer.closeDrawer();
    }

    @Override
    public int getCurrentSelectedID() {
        return drawer.getCurrentSelection();
    }

    public void updateSelectedToHome(){
        drawer.setSelection(DrawerItem.HOME.getId());
    }

    @Override
    public void clearAllFragments() {
        HomeFragment.clearChildFragments();
        LeisureFragment.clearChildFragments();
        LifeFragment.clearChildFragments();
        JxnugoFragment.clearChildFragments();
        LibraryFragment.clearChildFragments();
        EducationFragment.clearChildFragments();
        JobHomeFragment.clearChildFragments();
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
