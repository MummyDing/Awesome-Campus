package cn.edu.jxnu.awesome_campus.ui.settings;

import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class SettingsActivity extends BaseToolbarActivity{
    private static final String TAG="SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initToolbar();
        setToolbarTitle(InitApp.AppContext.getString(R.string.settings));

        getFragmentManager().beginTransaction().replace(R.id.framelayout,new SettingsFragment()).commit();

    }
}
