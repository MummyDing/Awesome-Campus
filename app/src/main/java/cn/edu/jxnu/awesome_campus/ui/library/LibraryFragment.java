package cn.edu.jxnu.awesome_campus.ui.library;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LibraryFragment extends TopNavigationFragment{

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
        fragments.add(new HotSearchFragment());
        fragments.add(new BookBorrowedFragment());
        fragments.add(new SelfStudySeatFragment());
    }

    public static LibraryFragment newInstance(){
        addChildFragments();
        return new LibraryFragment();
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.SWIPE_TO_LIBRARY_BORROWED:
                viewPager.setCurrentItem(1);
                pagerAdapter.notifyDataSetChanged();
                break;
        }
    }
}
