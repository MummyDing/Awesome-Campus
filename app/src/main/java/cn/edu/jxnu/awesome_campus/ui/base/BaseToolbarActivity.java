package cn.edu.jxnu.awesome_campus.ui.base;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.about.AboutFragment;

/**
 * Created by KevinWu on 16-4-21.
 */
public class BaseToolbarActivity extends SwipeBackActivity {
    protected Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    protected void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
        toolbar.setBackgroundColor(array.getColor(0,0xFFFFFF));
        else
            toolbar.setBackgroundColor(Color.BLACK);
        array.recycle();
    }
    public void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
