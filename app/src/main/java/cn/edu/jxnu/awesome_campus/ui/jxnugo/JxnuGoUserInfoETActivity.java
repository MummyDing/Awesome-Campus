package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.view.MenuItem;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * 个人信息修改界面
 * Created by KevinWu on 16-5-18.
 */
public class JxnuGoUserInfoETActivity extends BaseToolbarActivity{
    public static final String TAG="JxnuGoUserInfoETActivity";
    private static final String title="修改个人信息";
    private MenuItem doneMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jxnugo_userinfo);
        initToolbar();
        setToolbarTitle(title);
    }
}
