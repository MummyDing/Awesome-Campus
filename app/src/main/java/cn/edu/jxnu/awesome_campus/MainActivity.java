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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.common.DrawerItem;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenter;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenterImpl;
import cn.edu.jxnu.awesome_campus.support.CONSTANT;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.ImageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SettingsUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseActivity;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
import cn.edu.jxnu.awesome_campus.ui.education.EducationFragment;
import cn.edu.jxnu.awesome_campus.ui.home.HomeFragment;
import cn.edu.jxnu.awesome_campus.ui.leisure.LeisureFragment;
import cn.edu.jxnu.awesome_campus.ui.library.LibraryFragment;
import cn.edu.jxnu.awesome_campus.ui.life.LifeFragment;
import cn.edu.jxnu.awesome_campus.ui.login.LoginFragment;
import cn.edu.jxnu.awesome_campus.ui.settings.SettingsActivity;
import cn.edu.jxnu.awesome_campus.ui.study.StudyFragment;
import cn.edu.jxnu.awesome_campus.view.home.HomeView;
import cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog.ColorPickerDialog;
import cn.edu.jxnu.awesome_campus.view.widget.colorpickerdialog.OnColorChangedListener;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 *
 * Amended by KevinWu on 16-1-24
 */
public class MainActivity extends BaseActivity implements HomeView{

    private long lastPressTime = 0;

    private Toolbar toolbar;
    private Menu menu;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    public static HomePresenter presenter;
    private Settings mSettings = Settings.getsInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSettings.swipeID = mSettings.getInt(Settings.SWIPE_BACK,0);
        mSettings.isExitConfirm = mSettings.getBoolean(Settings.EXIT_CONFIRM,true);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        presenter = new HomePresenterImpl(this);
        presenter.initlization();
        presenter.buildDrawer(this,toolbar);
        switchDrawerItem(DrawerItem.HOME.getId());
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
        if( menu!= null) {
            menu.clear();
        }
        if(id == DrawerItem.HOME.getId()){
            presenter.clearAllFragments();
            switchFragment(HomeFragment.newInstance(),DrawerItem.HOME.getItemName());
        }else if(id == DrawerItem.LEISURE.getId()){
            // switch fragment
            presenter.clearAllFragments();
            switchFragment(LeisureFragment.newInstance(),DrawerItem.LEISURE.getItemName());
        }else if(id == DrawerItem.LIFE.getId()){
            presenter.clearAllFragments();
            switchFragment(LifeFragment.newInstance(),DrawerItem.LIFE.getItemName());
        }else if(id == DrawerItem.STUDY.getId()){
            presenter.clearAllFragments();
            switchFragment(StudyFragment.newInstance(),DrawerItem.STUDY.getItemName());
        }else if(id == DrawerItem.LIBRARY.getId()){
            // switch menu  搜索框 下拉主题还有点问题
            if(menu != null) {
                getMenuInflater().inflate(R.menu.menu_library, menu);
                SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                MenuItem searchItem = menu.findItem(R.id.menu_search);
                SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
            // switch fragment
            presenter.clearAllFragments();
            switchFragment(LibraryFragment.newInstance(),DrawerItem.LIBRARY.getItemName());
        }else if(id == DrawerItem.EDUCATION.getId()){
            presenter.clearAllFragments();
            switchFragment(EducationFragment.newInstance(),DrawerItem.EDUCATION.getItemName());
        }else if(id == DrawerItem.THEME.getId()){

            ColorPickerDialog dialog = new ColorPickerDialog(this, ThemeConfig.themeColor);


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
        }else if(id == DrawerItem.SETTINGS.getId()){
            //setTitle(DrawerItem.SETTINGS.getItemName());
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }else if(id == DrawerItem.LOGOUT.getId()){
            //setTitle(DrawerItem.LOGOUT.getItemName());
            EducationLoginUtil.clearCookie();
            LibraryLoginUtil.clearCookie();
            presenter.updateHeader(this);
        }
    }

    private void switchFragment(TopNavigationFragment fragment, String title){
        setTitle(title);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void switchToLogin() {
        fragmentTransaction = fragmentManager.beginTransaction();
        setTitle(getString(R.string.Login));
        fragmentTransaction.replace(R.id.framelayout, LoginFragment.newInstance());
        fragmentTransaction.commit();
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        return true;
    }

    @Override
    public void onBackPressed() {
        if(presenter.isDrawerOpen()){
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

}
