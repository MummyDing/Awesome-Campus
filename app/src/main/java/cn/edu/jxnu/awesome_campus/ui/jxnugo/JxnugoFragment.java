package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;
/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnugoFragment extends TopNavigationFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new GoodsListFragment());
        fragments.add(new CategoryListFragment());
//        fragments.add(new CollectionListsFragment());
    }

    public static JxnugoFragment newInstance(){
        addChildFragments();
        return new JxnugoFragment();
    }


    @Override
    public void onEventComing(EventModel eventModel) {

    }
}
