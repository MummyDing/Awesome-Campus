package cn.edu.jxnu.awesome_campus.ui.home;

import java.util.ArrayList;

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
        fragments.add(new CourseTableFragment());
        fragments.add(new CampusNewsFragment());
    }

    public static HomeFragment newInstance(){
        addChildFragments();
        return new HomeFragment();
    }

}
