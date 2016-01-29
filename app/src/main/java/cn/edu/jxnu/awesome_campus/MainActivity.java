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

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenter;
import cn.edu.jxnu.awesome_campus.presenter.home.HomePresenterImpl;
import cn.edu.jxnu.awesome_campus.ui.base.BaseActivity;
import cn.edu.jxnu.awesome_campus.view.home.HomeView;

/**
 * Created by MummyDing on 16-1-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 *
 * Amended by KevinWu on 16-1-24
 */
public class MainActivity extends BaseActivity implements HomeView{

    private Toolbar toolbar;
    private AccountHeader header;
    private Drawer drawer ;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private HomePresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new HomePresenterImpl(this);

        presenter.initlization();
    }

    @Override
    public void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /***
         * 测试用 非正式代码!!!!
         */
        header = new AccountHeaderBuilder().withActivity(this)
                .addProfiles(new ProfileDrawerItem().withIcon(R.mipmap.ic_launcher)
                .withName(getString(R.string.hint_click_to_login)))
                .withHeaderBackground(R.drawable.header)
                .build();
        drawer = new DrawerBuilder().withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("首页").withIcon(R.mipmap.ic_home_black_36dp))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("课余").withIcon(R.mipmap.ic_explore_black_24dp))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("生活").withIcon(R.mipmap.ic_store_black_36dp))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("图书馆").withIcon(R.mipmap.ic_local_library_black_36dp))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("教务处").withIcon(R.mipmap.ic_school_black))
                .addDrawerItems(
                        new DividerDrawerItem())
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("主题设置"))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("夜间模式"))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("设 置"))
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("注 销"))
                        .build();


    }
}
