package cn.edu.jxnu.awesome_campus.ui.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.MainActivity;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoLoginBean;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.registe.JxnuGoRegisteActivity;

/**
 * Created by root on 16-5-11.
 */
public class JxnuGoLoginFragment extends BaseLoginFragment {
    private static final  String TAG="JxnuGoLoginFragment";
    @Override
    protected void init() {
        super.init();
        setOnLineLayout(JxnuGoLoginUtil.isLogin());
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputAreaEnable(false);
                JxnuGoLoginUtil.onLogin(usernameET, passwordET);
            }
        });
        tips.setText("没有账号？请点击这里注册");
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), JxnuGoRegisteActivity.class);
                startActivity(intent);
            }
        });
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"跳转");
                EventBus.getDefault().post(new EventModel<Void>(EVENT.JUMP_TO_LOGIN_JXNUGO_USERINFO));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_login_jxnugo;
    }

    @Override
    protected String getUsernameHint() {
        return getResources().getString(R.string.text_username_or_email);
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.jxnugo);
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.JXNUGO_LOGIN_SUCCESS:
                setOnLineLayout(true);
                setInputAreaEnable(true);
                MainActivity.presenter.updateHeader(getActivity());
                DisplayUtil.Snack(getView(), InitApp.AppContext.getString(R.string.hint_login_successful));
                break;
            case EVENT.JXNUGO_LOGIN_FAILURE:
                setInputAreaEnable(true);
//                MainActivity.presenter.updateHeader(getActivity());
                DisplayUtil.Snack(getView(), "用户名/邮箱 或 密码 错误，请更改后重试");
                break;
            case EVENT.JXNUGO_LOGIN_FAILURE_SEVER_ERROR:
                setInputAreaEnable(true);
//                MainActivity.presenter.updateHeader(getActivity());
                DisplayUtil.Snack(getView(), "用户名 不存在或 密码 错误，请检查");
                break;
        }
    }
}
