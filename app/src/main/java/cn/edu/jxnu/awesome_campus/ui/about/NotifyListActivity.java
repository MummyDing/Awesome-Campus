package cn.edu.jxnu.awesome_campus.ui.about;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;
import cn.edu.jxnu.awesome_campus.ui.base.SwipeBackActivity;

public class NotifyListActivity extends BaseToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_list);
        initToolbar();
//        setToolbarTitle(InitApp.AppContext.getString(R.string.about));
        setToolbarTitle("通知列表");
    }
}
