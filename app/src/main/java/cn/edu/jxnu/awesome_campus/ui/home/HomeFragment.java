package cn.edu.jxnu.awesome_campus.ui.home;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HomeFragment extends TopNavigationFragment {

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        if(EducationLoginUtil.isLogin()){
            fragments.add(new CourseTableFragment());
            fragments.add(new CampusNewsFragment());
        }else{
            fragments.add(new CampusNewsFragment());
            fragments.add(new CourseTableFragment());
        }

    }

    public static HomeFragment newInstance(){
        addChildFragments();
        return new HomeFragment();
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
