package cn.edu.jxnu.awesome_campus.ui.settings;

import android.os.Bundle;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

public class SettingsActivity extends BaseToolbarActivity{
    private static final String TAG="SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        initToolbar();
        setToolbarTitle(InitApp.AppContext.getString(R.string.settings));

        getFragmentManager().beginTransaction().replace(R.id.framelayout,new SettingsFragment()).commit();

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().post(new EventModel<Void>(EVENT.UPDATE_SELECTED_MENU_TO_HOME));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
    }
}
