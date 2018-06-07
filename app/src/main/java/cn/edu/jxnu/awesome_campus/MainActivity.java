/*
 * Copyright (c) 2016.  MummyDing & KevinWu
 *    Awesome-Campus is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *    Awesome-Campus is distributed in the hope that it will be useful,but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 */

package cn.edu.jxnu.awesome_campus;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.actionitembadge.library.ActionItemBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.dao.life.WeatherInfoDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.common.DrawerItem;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenter;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenterImpl;
import cn.edu.jxnu.awesome_campus.support.CONSTANT;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.service.NotifyService;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.NotifyUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.PollingUtils;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SystemUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.about.AboutActivity;
import cn.edu.jxnu.awesome_campus.ui.about.NotifyListActivity;
import cn.edu.jxnu.awesome_campus.ui.base.BaseActivity;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
import cn.edu.jxnu.awesome_campus.ui.education.EducationFragment;
import cn.edu.jxnu.awesome_campus.ui.home.HomeFragment;
import cn.edu.jxnu.awesome_campus.ui.home.SelectTermDialog;
import cn.edu.jxnu.awesome_campus.ui.job.JobHomeFragment;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.GoodsSearchResultActivity;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.JxnugoFragment;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.JxnuGoUserinfoActivity;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.NewGoodsActivity;
import cn.edu.jxnu.awesome_campus.ui.leisure.LeisureFragment;
import cn.edu.jxnu.awesome_campus.ui.library.LibraryFragment;
import cn.edu.jxnu.awesome_campus.ui.life.LifeFragment;
import cn.edu.jxnu.awesome_campus.ui.login.LoginFragment;
import cn.edu.jxnu.awesome_campus.ui.settings.SettingsActivity;
import cn.edu.jxnu.awesome_campus.view.home.HomeView;
import cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog.ColorPickerDialog;
import cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog.OnColorChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.dialog.AppUpdateDialog;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 *
 * Amended by KevinWu on 16-1-24
 */
public class MainActivity extends BaseActivity implements HomeView{

    private long lastPressTime = 0;
    private static final String TAG="MainActivity";
    private Toolbar toolbar;
    private Menu menu;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    public static HomePresenter presenter;
    private Settings mSettings = Settings.getsInstance();
    private MenuItem selectTerm;
    private MenuItem notifyMenu;

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    private MenuItem searchMenu;
    private MenuItem newGoodsMenu;
    private MenuItem userInfoMenu;
    private MenuItem searchGoods;
    private int nowDrawID=DrawerItem.HOME.getId();
    private CheckBox educationCheckBox,libraryCheckBox,jxnugoCheckBox;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Settings.autoRefresh = mSettings.getBoolean(Settings.AUTO_REFRESH,true);
        Settings.swipeID = mSettings.getInt(Settings.SWIPE_BACK,0);
        Settings.isExitConfirm = mSettings.getBoolean(Settings.EXIT_CONFIRM,true);
        Settings.avatorID = mSettings.getInt(Settings.AVATAR,0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  检查更新
         */
        SystemUtil.tryCheckUpdate();

        /**
         * 针对1.1改变了图书馆的账号的登录方式，所以做此特殊处理
         */
        if(SystemUtil.getVersionCode()>1.1){
            SPUtil spu = new SPUtil(InitApp.AppContext);
            String id=spu.getStringSP(LibraryStaticKey.SP_FILE_NAME,LibraryStaticKey.ID);
            if(id==null){
                LibraryLoginUtil.clearCookie();
            }
        }
        if (SystemUtil.getVersionCode() != mSettings.getInt(Settings.INTRO_VERSION,0)) {
            mSettings.putInt(Settings.INTRO_VERSION,SystemUtil.getVersionCode());
            Intent intent = new Intent(MainActivity.this, AppGuideActivity.class);
            startActivity(intent);
        }
        EventBus.getDefault().register(this);
        //一个小时启动一次
        PollingUtils.startPollingService(this, 3600, NotifyService.class, NotifyService.ACTION);

        presenter = new HomePresenterImpl(this);
        presenter.initlization();
        presenter.buildDrawer(this,toolbar);
        switchDrawerItem(DrawerItem.HOME.getId());
        WeatherInfoDAO dao = new WeatherInfoDAO();
        dao.loadFromNet();

    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void switchDrawerItem(int id) {
        nowDrawID=id;
        if( menu!= null) {
            menu.clear();
        }
        if(id == DrawerItem.HOME.getId()){

            presenter.clearAllFragments();
            switchFragment(HomeFragment.newInstance(),DrawerItem.HOME.getItemName());
//            setNotify();
        }else if(id == DrawerItem.LEISURE.getId()){
            // switch fragment
            presenter.clearAllFragments();
            switchFragment(LeisureFragment.newInstance(),DrawerItem.LEISURE.getItemName());
        }else if(id == DrawerItem.LIFE.getId()){
            presenter.clearAllFragments();
            switchFragment(LifeFragment.newInstance(),DrawerItem.LIFE.getItemName());
        }else if(id == DrawerItem.JXNUGO.getId()){
            presenter.clearAllFragments();
            switchFragment(JxnugoFragment.newInstance(),DrawerItem.JXNUGO.getItemName());

        }else if(id == DrawerItem.LIBRARY.getId()){

            // switch fragment
            presenter.clearAllFragments();
            switchFragment(LibraryFragment.newInstance(),DrawerItem.LIBRARY.getItemName());
            // switch menu  搜索框 下拉主题还有点问题
//            updateMenu();
        }else if(id == DrawerItem.EDUCATION.getId()){
            presenter.clearAllFragments();
            switchFragment(EducationFragment.newInstance(),DrawerItem.EDUCATION.getItemName());
        }else if(id == DrawerItem.THEME.getId()){
            showThemePickerDialog();
        }else if(id == DrawerItem.SETTINGS.getId()){
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(id == DrawerItem.ABOUT.getId()){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        else if(id == DrawerItem.LOGOUT.getId()){
            showLogoutDialog();
        }else if(id==DrawerItem.JOB.getId()){
            switchToJob();
        }
        Log.d(TAG,"switchDrawerItem");
        setMenu();
    }


    private void showThemePickerDialog(){
        ColorPickerDialog dialog = new ColorPickerDialog(this, ThemeConfig.themeColor);
        dialog.setTitle(InitApp.AppContext.getString(R.string.theme));

        dialog.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void onColorChanged(int newColor) {
                if (newColor == ThemeConfig.themeColor[Config.themeSelected]){
                    return;
                }
                SPUtil sp=new SPUtil(MainActivity.this);
                int selectColor=0;
                for(int i=0;i<ThemeConfig.themeColor.length;i++){
                    if(ThemeConfig.themeColor[i]==newColor){
                        selectColor=i;
                        break;
                    }
                }
                sp.putIntSP(Config.SP_FILE_NAME,Config.THEME_SELECTED,selectColor);
                recreate();
            }
        });
        dialog.build().show();
        dialog.setCheckedColor(ThemeConfig.themeColor[Config.themeSelected]);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                EventBus.getDefault().post(new EventModel<Void>(EVENT.UPDATE_SELECTED_MENU_TO_HOME));
            }
        });
    }

    private void showLogoutDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);// 渲染器
        View view= inflater.inflate(R.layout.widget_logout_layout,
                null);
        initview(view);
        AlertDialog dialog =  new AlertDialog.Builder(this)
//                .setIcon(R.drawable.logo)
                .setTitle(getString(R.string.logout_title))
//                .setMultiChoiceItems()
//                .setMessage(getString(R.string.notify_sure_to_logout))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (educationCheckBox.isChecked()){
                            EducationLoginUtil.clearCookie();
                            DatabaseHelper.clearUserData();
                        }
                        if(libraryCheckBox.isChecked()){
                            LibraryLoginUtil.clearCookie();
                            DatabaseHelper.clearLibraryData();
                        }
                        if(jxnugoCheckBox.isChecked())
                        JxnuGoLoginUtil.clearInfo();

                        if(!LibraryLoginUtil.isLogin()&&
                                !EducationLoginUtil.isLogin()&&
                                !LibraryLoginUtil.isLogin()){
                            presenter.updateHeader(MainActivity.this);
                        }

                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setView(view);
        dialog.getWindow().setLayout(3*DisplayUtil.getScreenWidth(this)/4,-2);
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                presenter.updateSelectedToHome();
            }
        });
    }

    /**
     * 设置注销对话框view
     * @param view
     * @return
     */
    private void initview(View view) {
        educationCheckBox=(CheckBox)view.findViewById(R.id.education_checkBox);
        libraryCheckBox=(CheckBox)view.findViewById(R.id.library_checkBox);
        jxnugoCheckBox=(CheckBox)view.findViewById(R.id.jxnugo_checkBox);
        if(EducationLoginUtil.isLogin()){
            educationCheckBox.setEnabled(true);
        }else{
            educationCheckBox.setEnabled(false);
        }
        if(LibraryLoginUtil.isLogin()){
            libraryCheckBox.setEnabled(true);
        }else{
            libraryCheckBox.setEnabled(false);
        }
        if(JxnuGoLoginUtil.isLogin()){
            jxnugoCheckBox.setEnabled(true);
        }else{
            jxnugoCheckBox.setEnabled(false);
        }

    }

    private void switchFragment(TopNavigationFragment fragment, String title){
        setTitle(title);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }



    public void switchToJob(){
        fragmentTransaction = fragmentManager.beginTransaction();
        setTitle(getString(R.string.job_jobtitle));
        fragmentTransaction.replace(R.id.framelayout, JobHomeFragment.newInstance());
        fragmentTransaction.commit();
        if (menu != null) menu.clear();
    }


    @Override
    public void switchToLogin() {
        fragmentTransaction = fragmentManager.beginTransaction();
        setTitle(getString(R.string.Login));
        fragmentTransaction.replace(R.id.framelayout, LoginFragment.newInstance());
        fragmentTransaction.commit();
        if (menu != null) menu.clear();
    }

    public void switchToLibrary(){
        switchFragment(LibraryFragment.newInstance(),getString(R.string.library));
    }


    public void jumpToJxnugoUserinfo(EventModel eventModel){
        Intent intent=new Intent(this, JxnuGoUserinfoActivity.class);
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().postSticky(new EventModel<Void>(EVENT.JXNUGO_USERINFO_LOAD_LOGIN_USER));
            }
        });
        startActivity(intent);
    }


    private static final Handler handler = new Handler(Looper.getMainLooper());

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
        switch (eventModel.getEventCode()){
            case EVENT.JUMP_TO_MAIN:
                Log.d(TAG,"跳转到主菜单");
                presenter.clearAllFragments();
                switchFragment(HomeFragment.newInstance(),DrawerItem.HOME.getItemName());
                nowDrawID=DrawerItem.HOME.getId();
                break;
            case EVENT.JUMP_TO_EDUCATION_LOGIN:
                switchToLogin();
                nowDrawID=0;
                break;
            case EVENT.JUMP_TO_JXNUGO_LOGIN:
                switchToLogin();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<String>(EVENT.SWIPE_TO_JXNUGO_LOGIN));
                    }
                });
                nowDrawID=0;
                 break;
            case EVENT.JUMP_TO_LIBRARY_LOGIN:
                switchToLogin();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<String>(EVENT.SWIPE_TO_LIBRARY_LOGIN));
                    }
                });
                nowDrawID=0;
                break;
            case EVENT.JUMP_TO_LIBRARY_BORROWED:
                switchToLibrary();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<String>(EVENT.SWIPE_TO_LIBRARY_BORROWED));
                    }
                });
                nowDrawID=0;
                break;
            case EVENT.JUMP_TO_LOGIN_JXNUGO_USERINFO:
                jumpToJxnugoUserinfo(eventModel);
                nowDrawID=0;
                break;
            case EVENT.UPDATE_SELECTED_MENU_TO_HOME:
                presenter.updateSelectedToHome();
                nowDrawID=DrawerItem.HOME.getId();
                break;
            case EVENT.VERSION_CHECK_NEED_UPDATE:
                showNewVersionDialog((String) eventModel.getData());
                break;

        }
        setMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // 这里根据是否有消息进行菜单显示
        getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
        newGoodsMenu=menu.findItem(R.id.menu_new_goods);
        selectTerm = menu.findItem(R.id.menu_select_term);
        notifyMenu=menu.findItem(R.id.menu_notify);
        searchMenu=menu.findItem(R.id.menu_search);
        userInfoMenu=menu.findItem(R.id.menu_user_info);
        searchGoods=menu.findItem(R.id.menu_search_goods);
        Log.d(TAG,"初始化菜单");
        setMenu();
        return true;
    }

    private void setMenu() {
        Log.d(TAG,"当前ID为："+nowDrawID);
        if(selectTerm != null && notifyMenu!=null&&searchMenu!=null&&newGoodsMenu!=null){
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_mainactivity, menu);
            selectTerm = menu.findItem(R.id.menu_select_term);
            notifyMenu=menu.findItem(R.id.menu_notify);
            searchMenu=menu.findItem(R.id.menu_search);
            newGoodsMenu=menu.findItem(R.id.menu_new_goods);
            userInfoMenu=menu.findItem(R.id.menu_user_info);
            searchGoods=menu.findItem(R.id.menu_search_goods);
        if(nowDrawID==DrawerItem.HOME.getId()){
            Log.d(TAG,"主页菜单切换");
            if (EducationLoginUtil.isLogin()) {
                selectTerm.setVisible(true);
            }
            notifyMenu.setVisible(true);
            setNotify();
        }else if(nowDrawID==DrawerItem.LIBRARY.getId()){
            Log.d(TAG,"图书馆菜单切换");
            searchMenu.setVisible(true);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }else if(nowDrawID==DrawerItem.JXNUGO.getId()){
            Log.d(TAG,"二手菜单切换");
            if(JxnuGoLoginUtil.isLogin()){
                newGoodsMenu.setVisible(true);
                searchGoods.setVisible(true);
                setGoodsSearchView();
            }

            userInfoMenu.setVisible(true);
        }}
    }

    /**
     * 设置显示的消息数
     */
    private void setNotify(int...data) {
        Log.d(TAG,"更新消息数");
        if(NotifyUtil.hasUnread()>0&&nowDrawID==DrawerItem.HOME.getId()){
            ActionItemBadge.update(this, notifyMenu, ContextCompat.getDrawable(this, R.mipmap.ic_notify_none), ActionItemBadge.BadgeStyles.YELLOW, NotifyUtil.hasUnread());
        }else if(data.length>0&&nowDrawID==DrawerItem.HOME.getId()){
            ActionItemBadge.update(this, notifyMenu, ContextCompat.getDrawable(this, R.mipmap.ic_notify_none), ActionItemBadge.BadgeStyles.YELLOW,data[0]);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_select_term:
                Intent intent = new Intent(this, SelectTermDialog.class);
                startActivity(intent);
                break;
            case R.id.menu_notify:
                intent = new Intent(this, NotifyListActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_new_goods:
                startActivity(new Intent(this, NewGoodsActivity.class));
                break;
            case R.id.menu_user_info:
                if(JxnuGoLoginUtil.isLogin()){
                    SPUtil sp=new SPUtil(MainActivity.this);
                    int userId=sp.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
                    EventBus.getDefault().postSticky(new EventModel<Integer>(EVENT.JXNUGO_USERINFO_LOAD_USER,userId));
                    Intent intent2=new Intent(MainActivity.this, JxnuGoUserinfoActivity.class);
                    startActivity(intent2);
                }else{
                    //未登录时跳转到登录界面
                    switchToLogin();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventModel<String>(EVENT.SWIPE_TO_JXNUGO_LOGIN));
                        }
                    });
                    setMenu();
                }
                break;
            case R.id.menu_search_goods:
                searchView.showSearch();
                break;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        if (searchView!=null&&searchView.isSearchOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        }else if(presenter.isDrawerOpen()){
            presenter.closeDrawer();
        }else if(canExit()){
            super.onBackPressed();
        }
    }

    private boolean canExit(){
        if(Settings.isExitConfirm){
            if(System.currentTimeMillis() - lastPressTime > CONSTANT.exitConfirmTime){
                lastPressTime = System.currentTimeMillis();
                Snackbar.make(getCurrentFocus(), R.string.notify_exit_confirm,Snackbar.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Settings.needRecreate) {
            Settings.needRecreate = false;
            this.recreate();
        }

        if (Settings.needUpdateAvatar){
            Settings.needUpdateAvatar = false;
            if (JxnuGoLoginUtil.isLogin()||EducationLoginUtil.isLogin()){
                presenter.updateHeader(this);
            }
        }
        Log.d(TAG,"onResume");
        setMenu();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        PollingUtils.stopPollingService(this,NotifyService.class,NotifyService.ACTION);
//        TCAgent.onPageEnd(InitApp.AppContext, TAG);加用户统计会导致小部件异常
        super.onDestroy();
    }

    private void setGoodsSearchView(){
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setHint("搜索商品");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG,"查询字符串："+query);
                Intent intent=new Intent(MainActivity.this, GoodsSearchResultActivity.class);
                intent.putExtra("key",query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private void showNewVersionDialog(final String newVersion){
        handler.post(new Runnable() {
            @Override
            public void run() {
                AppUpdateDialog appUpdateDialog = new AppUpdateDialog(MainActivity.this, newVersion);
                appUpdateDialog.show();
            }
        });
    }
}
