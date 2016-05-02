package cn.edu.jxnu.awesome_campus.ui.about;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

public class AboutActivity extends BaseToolbarActivity {
    private static final String TAG="AboutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_about);
        initToolbar();
        setToolbarTitle(InitApp.AppContext.getString(R.string.about));
        getFragmentManager().beginTransaction().replace(R.id.framelayout,new AboutFragment()).commit();
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
