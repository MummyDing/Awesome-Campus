package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * 个人信息修改界面
 * Created by KevinWu on 16-5-18.
 */
public class JxnuGoUserInfoETActivity extends BaseToolbarActivity{
    public static final String TAG="JxnuGoUserInfoETActivity";
    private static final String title="修改个人信息";
    private MenuItem doneMenu;
    private EditText nicknameET,locationET,contactET,aboutmeET;
    private SimpleDraweeView avatarImageView;
    private JxnuGoPeopleModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_edit_jxnugo_userinfo);
        initToolbar();
        setToolbarTitle(title);
        importData();
    }

    /**
     * 导入数据
     */
    private void importData() {
        SPUtil sp=new SPUtil(this);
        int userId=sp.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        model=new JxnuGoPeopleModel();
        model.loadFromNet();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.JXNUGO_USERINFO_LOAD_USER_SUCCESS:
                JxnuGoPeopleBean jb=(JxnuGoPeopleBean)eventModel.getData();
                model=new JxnuGoPeopleModel(jb.getAboutMe(),jb.getUrlLinkUser(),jb.getUserAvatar(),jb.getUserId(),jb.getUserName());
                fillData();
                break;
            case EVENT.JXNUGO_USERINFO_LOAD_USER_FALURE:
                break;
        }

    }

    private void fillData() {
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
