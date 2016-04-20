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
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class SettingsActivity extends SwipeBackActivity {
    private static final String TAG="SettingsActivity";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.setting);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary
        });
        toolbar.setBackgroundColor(array.getColor(0,0xffffff));

        array.recycle();

        getFragmentManager().beginTransaction().replace(R.id.framelayout,new SettingsFragment()).commit();

    }
}
