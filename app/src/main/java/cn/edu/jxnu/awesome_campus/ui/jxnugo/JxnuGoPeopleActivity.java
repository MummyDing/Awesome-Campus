package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleLoad;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by Thereisnospon on 16/5/14.
 * 显示用户关注的人的信息或者粉丝信息
 *
 * 曲线救国虽然已经解决当前bug，日后待重构（@KevinWu）
 */
public class JxnuGoPeopleActivity extends BaseToolbarActivity {
    public static final String TAG = "JxnuGoPeopleActivity";
    private int id;
    private JxnuGoPeopleDao.MODE mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setContentView(R.layout.activity_jxnugo_people);
        initToolbar();
        id = getIntent().getIntExtra("id", -1);
        Log.e(TAG,"执行初始化"+id);
        getMsg();
    }

    private void getMsg() {
        EventModel model = EventBus.getDefault().getStickyEvent(EventModel.class);
        if (model != null && model.getEventCode() == EVENT.JUMP_TO_JXNUGO_LOAD_POEPLE) {
            JxnuGoPeopleLoad data = (JxnuGoPeopleLoad) model.getData();
            Log.e(TAG, "传入的id是 ："+id);
            Log.e(TAG,"当前事件的id是 ："+data.getId());
            if (id == data.getId()) {
                mode=data.getMode();
                if (data.getMode() == JxnuGoPeopleDao.MODE.FOLLOWED) {
                    setToolbarTitle(getString(R.string.jxnugo_user_followed));
                } else {
                    setToolbarTitle(getString(R.string.jxnugo_user_followers));
                }
                initView(mode,id);
            }
        }
    }

    private void initView(JxnuGoPeopleDao.MODE mode,int id) {
        FragmentManager manager = getSupportFragmentManager();
        JxnuGoPeopleFragment fragment = JxnuGoPeopleFragment.newInstance(mode,id);
        manager.beginTransaction()
                .replace(R.id.fragment_content, fragment)
                .commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView(mode,id);
    }

    @Override
    protected void onDestroy() {
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }
}
