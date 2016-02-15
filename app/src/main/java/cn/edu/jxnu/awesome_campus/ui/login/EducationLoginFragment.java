package cn.edu.jxnu.awesome_campus.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationLoginFragment extends BaseFragment{

    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;
    private RelativeLayout loginLayout;
    private RelativeLayout onLineLayout;
    @Override
    protected void init() {

        usernameET = (EditText) parentView.findViewById(R.id.et_studentID);
        passwordET = (EditText) parentView.findViewById(R.id.et_password);
        loginBtn = (Button) parentView.findViewById(R.id.loginBtn);

        loginLayout = (RelativeLayout) parentView.findViewById(R.id.loginLayout);
        onLineLayout = (RelativeLayout) parentView.findViewById(R.id.onLineLayout);

        setOnLineLayout(EducationLoginUtil.isLogin());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里还要加点登录时的效果 progressbar 或是其他动画什么的
                setInputAreaEnable(false);
                EducationLoginUtil.onLogin(usernameET,passwordET);
            }
        });
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.education);
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        // 这里 Event Code 名字需要更改
        switch (eventModel.getEventCode()){
            case EVENT.EDUCATION_LOGIN_SUCCESS:
                setOnLineLayout(true);
                setInputAreaEnable(true);
                DisplayUtil.Snack(getView(),"Login Successful!!!");
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NETWORK_ERROR:
                setLoginFailureLayout();
                DisplayUtil.Snack(getView(),"Network Error!!!");
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NO_ID:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,"Wrong Student ID!!!");
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NULL_INPUT:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,"Input cannot be null");
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_PASSWORD_INCORRECT:
                setLoginFailureLayout();
                DisplayUtil.Snack(passwordET,"Wrong Password!!!");
                break;
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_login_education;
    }

    private void setInputAreaEnable(boolean flag){
        usernameET.setEnabled(flag);
        passwordET.setEnabled(flag);
    }

    private void setLoginFailureLayout(){
        setOnLineLayout(false);
        setInputAreaEnable(true);
    }

    private void setOnLineLayout(boolean flag){
        Log.d("Is OnLine",flag+"");
        if(flag){
            loginLayout.setVisibility(View.GONE);
            onLineLayout.setVisibility(View.VISIBLE);
        }else {
            loginLayout.setVisibility(View.VISIBLE);
            onLineLayout.setVisibility(View.GONE);
        }
    }

}
