package cn.edu.jxnu.awesome_campus.ui.login;

import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.MainActivity;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.DisplayUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryLoginFragment extends BaseLoginFragment{

    @Override
    protected void init() {
        super.init();
        setOnLineLayout(LibraryLoginUtil.isLogin());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里还要加点登录时的效果 progressbar 或是其他动画什么的
                setInputAreaEnable(false);
                LibraryLoginUtil.onLogin(usernameET,passwordET);
            }
        });
    }

    @Override
    protected String getUsernameHint() {
        return "Username";
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.library);
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.LIBRARY__LOGIN_FAILURE_NULL_INPUT:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_null_input));
                break;
            case EVENT.LIBRARY_LOGIN_FAILURE:
                setLoginFailureLayout();
                DisplayUtil.Snack(usernameET,InitApp.AppContext.getString(R.string.hint_login_failed));
                break;
            case EVENT.LIBRARY_LOGIN_FAILURE_NETWORKERROR:
                setLoginFailureLayout();
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_network_error));
                break;
            case EVENT.LIBRARY_LOGIN_SUCCESS:
                setOnLineLayout(true);
                setInputAreaEnable(true);
                MainActivity.presenter.updateHeader(getActivity());
                DisplayUtil.Snack(getView(),InitApp.AppContext.getString(R.string.hint_login_successful));
                break;
        }
    }
}
