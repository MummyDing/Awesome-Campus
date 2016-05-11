package cn.edu.jxnu.awesome_campus.ui.login;

import android.content.Intent;
import android.view.View;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.login.JxnuGoLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.registe.JxnuGoRegisteActivity;

/**
 * Created by root on 16-5-11.
 */
public class JxnuGoLoginFragment extends BaseLoginFragment {

    @Override
    protected void init() {
        super.init();
        setOnLineLayout(false);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInputAreaEnable(false);
                JxnuGoLoginUtil.onLogin(usernameET, passwordET);
            }
        });
        tips.setText("没有账号？请注册");
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), JxnuGoRegisteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected String getUsernameHint() {
        return getResources().getString(R.string.text_username);
    }

    @Override
    public String getTitle() {
        return "Market";
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
