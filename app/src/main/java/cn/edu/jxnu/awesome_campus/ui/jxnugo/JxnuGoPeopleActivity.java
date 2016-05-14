package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleLoad;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by Thereisnospon on 16/5/14.
 * 显示用户关注的人的信息活着粉丝信息
 */
public class JxnuGoPeopleActivity extends BaseToolbarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxnugo_people);
        initToolbar();
        getMsg();
    }
    void getMsg(){
       EventModel model= EventBus.getDefault().getStickyEvent(EventModel.class);
        if(model!=null&&model.getEventCode()== EVENT.JUMP_TO_JXNUGO_LOAD_POEPLE){
            JxnuGoPeopleLoad data=(JxnuGoPeopleLoad)model.getData();
            Log.d("JXNU_GO","get msg");
            if(data.getMode() ==JxnuGoPeopleDao.MODE.FOLLOWED){
                setToolbarTitle(getString(R.string.jxnugo_user_followed));
            }else{
                setToolbarTitle(getString(R.string.jxnugo_user_followers));
            }
            initView(data);
        }
    }
    void initView(JxnuGoPeopleLoad load){
        FragmentManager manager=getSupportFragmentManager();
        JxnuGoPeopleFragment fragment=JxnuGoPeopleFragment.newInstance(load.getMode(),load.getId());
        manager.beginTransaction()
                .replace(R.id.fragment_content,fragment)
                .commit();
    }
}
