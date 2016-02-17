package cn.edu.jxnu.awesome_campus.ui.login;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryLoginFragment extends BaseLoginFragment{
    @Override
    protected String getUsernameHint() {
        return "Username";
    }

    @Override
    public String getTitle() {
        return getString(R.string.library);
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
