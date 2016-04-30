package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

public class NotifyListActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);
        initToolbar();
        setToolbarTitle(getString(R.string.notify_list));
    }
}
