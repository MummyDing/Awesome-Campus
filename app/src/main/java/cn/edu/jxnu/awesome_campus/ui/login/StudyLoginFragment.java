package cn.edu.jxnu.awesome_campus.ui.login;

import android.view.View;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.login.LibraryLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class StudyLoginFragment extends BaseLoginFragment {
    @Override
    protected String getUsernameHint() {
        return "Username";
    }

    @Override
    protected void init() {
        super.init();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里还要加点登录时的效果 progressbar 或是其他动画什么的
                setInputAreaEnable(false);
                SelfStudyRoomLoginUtil.onLogin(usernameET,passwordET);
            }
        });
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.study);
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }

}
