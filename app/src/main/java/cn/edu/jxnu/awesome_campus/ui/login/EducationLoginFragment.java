package cn.edu.jxnu.awesome_campus.ui.login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.MainActivity;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationLoginFragment extends BaseLoginFragment{

    @Override
    protected String getUsernameHint() {
        return getString(R.string.studentid);
    }

    @Override
    protected void init() {
        super.init();
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
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_login_successful));
                MainActivity.presenter.updateHeader(getActivity());
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NETWORK_ERROR:
                setLoginFailureLayout();
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_network_error));
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NO_ID:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_wrong_studentid));
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_NULL_INPUT:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_null_input));
                break;
            case EVENT.EDUCATION_LOGIN_FAILURE_PASSWORD_INCORRECT:
                setLoginFailureLayout();
                DisplayUtil.Snack(passwordET,InitApp.AppContext.getString(R.string.hint_wrong_password));
                break;
            case EVENT.EDUCATION_LOGIN_SERVER_ERROR:
                setLoginFailureLayout();
                DisplayUtil.Snack(passwordET,InitApp.AppContext.getString(R.string.server_error));
                break;
        }
    }



}
