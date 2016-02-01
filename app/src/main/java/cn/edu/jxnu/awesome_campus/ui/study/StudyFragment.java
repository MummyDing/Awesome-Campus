package cn.edu.jxnu.awesome_campus.ui.study;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class StudyFragment extends TopNavigationFragment {


    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new SeatFragment());
        fragments.add(new MySeatFragment());
    }

    public static StudyFragment newInstance(){
        addChildFragments();
        return new StudyFragment();
    }
}
