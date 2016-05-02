package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

public class NotifyListActivity extends BaseToolbarActivity {

    private static final String TAG="NotifyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TCAgent.onPageStart(InitApp.AppContext, TAG);


        setContentView(R.layout.activity_notify_list);
        initToolbar();
        setToolbarTitle(getString(R.string.notify_list));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}
