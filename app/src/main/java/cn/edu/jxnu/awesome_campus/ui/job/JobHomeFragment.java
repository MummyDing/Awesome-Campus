package cn.edu.jxnu.awesome_campus.ui.job;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
import cn.edu.jxnu.awesome_campus.ui.login.EducationLoginFragment;
import cn.edu.jxnu.awesome_campus.ui.login.JxnuGoLoginFragment;
import cn.edu.jxnu.awesome_campus.ui.login.LibraryLoginFragment;
import cn.edu.jxnu.awesome_campus.ui.login.LoginFragment;

/**
 * Created by yzr on 16/10/16.
 */

public class JobHomeFragment extends TopNavigationFragment{


    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(JobFragment.newInstance(1));
        fragments.add(JobFragment.newInstance(2));
    }

    public static JobHomeFragment newInstance(){
        addChildFragments();
        return new JobHomeFragment();
    }


    @Override
    public void onEventComing(EventModel eventModel) {

    }


}
