package cn.edu.jxnu.awesome_campus.ui.about;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class AboutActivity extends SwipeBackActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary,
        });
        toolbar.setBackgroundColor(array.getColor(0,0xFFFFFF));
        array.recycle();
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new AboutFragment()).commit();

    }
}
