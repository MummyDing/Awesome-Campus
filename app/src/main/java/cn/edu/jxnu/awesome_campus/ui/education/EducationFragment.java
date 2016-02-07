package cn.edu.jxnu.awesome_campus.ui.education;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class EducationFragment extends TopNavigationFragment{

    private static EducationFragment educationFragment;

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new ExamFragment());
        fragments.add(new CourseScoreFragment());
    }


    public static EducationFragment newInstance(){
        addChildFragments();
        return new EducationFragment();
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
