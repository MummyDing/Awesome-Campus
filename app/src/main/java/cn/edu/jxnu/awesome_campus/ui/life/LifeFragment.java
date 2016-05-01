package cn.edu.jxnu.awesome_campus.ui.life;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LifeFragment extends TopNavigationFragment{

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new WeatherFragment());
        fragments.add(new CampusExpressFragment());
        fragments.add(new ATMFragment());

    }

    public static LifeFragment newInstance(){
        addChildFragments();
        return new LifeFragment();
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
