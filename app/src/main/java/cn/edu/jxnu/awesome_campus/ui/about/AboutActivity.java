package cn.edu.jxnu.awesome_campus.ui.about;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class AboutActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initToolbar();
        setToolbarTitle(InitApp.AppContext.getString(R.string.about));
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new AboutFragment()).commit();
    }

}
