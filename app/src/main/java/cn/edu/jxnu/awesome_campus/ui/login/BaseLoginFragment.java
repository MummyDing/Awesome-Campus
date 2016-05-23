package cn.edu.jxnu.awesome_campus.ui.login;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseLoginFragment extends BaseFragment{
    private static final String TAG="BaseLoginFragment";

    protected EditText usernameET;
    protected EditText passwordET;
    protected Button loginBtn;
    protected RelativeLayout loginLayout;
    protected RelativeLayout onLineLayout;

    protected TextInputLayout userNameLayout;
    protected Button jumpBtn;
    protected TextView tips;
    protected abstract String getUsernameHint();
    @Override
    protected void init() {

        userNameLayout = (TextInputLayout) parentView.findViewById(R.id.usernameLayout);
        usernameET = (EditText) parentView.findViewById(R.id.et_username);
        passwordET = (EditText) parentView.findViewById(R.id.et_password);
        loginBtn = (Button) parentView.findViewById(R.id.loginBtn);
        jumpBtn = (Button) parentView.findViewById(R.id.jumpBtn);
        tips = (TextView) parentView.findViewById(R.id.tips);

        loginLayout = (RelativeLayout) parentView.findViewById(R.id.loginLayout);
        onLineLayout = (RelativeLayout) parentView.findViewById(R.id.onLineLayout);

        userNameLayout.setHint(getUsernameHint());
        jumpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"跳转");
                EventBus.getDefault().post(new EventModel<String>(EVENT.JUMP_TO_MAIN));
            }
        });
    }


    @Override
    protected int getLayoutID() {
        return R.layout.layout_login;
    }

    protected void setInputAreaEnable(boolean flag){
        usernameET.setEnabled(flag);
        passwordET.setEnabled(flag);
    }

    protected void setLoginFailureLayout(){
        setOnLineLayout(false);
        setInputAreaEnable(true);
    }

    protected void setOnLineLayout(boolean flag){
        if(flag){
            loginLayout.setVisibility(View.GONE);
            onLineLayout.setVisibility(View.VISIBLE);
        }else {
            loginLayout.setVisibility(View.VISIBLE);
            onLineLayout.setVisibility(View.GONE);
        }
    }


}
