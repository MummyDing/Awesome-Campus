package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnugoFragment extends TopNavigationFragment {


    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new GoodsListFragment());
    }

    public static JxnugoFragment newInstance(){
        addChildFragments();
        return new JxnugoFragment();
    }


    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
