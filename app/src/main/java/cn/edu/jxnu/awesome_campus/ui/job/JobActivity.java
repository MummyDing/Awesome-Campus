package cn.edu.jxnu.awesome_campus.ui.job;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

public class JobActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        initToolbar();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_content,new JobFragment())
                .commit();
    }
}
