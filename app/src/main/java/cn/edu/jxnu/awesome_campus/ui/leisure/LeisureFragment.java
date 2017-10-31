package cn.edu.jxnu.awesome_campus.ui.leisure;

import java.util.ArrayList;

import cn.edu.jxnu.awesome_campus.database.dao.leisure.DailyDAO;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.TopNavigationFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class LeisureFragment extends TopNavigationFragment{

    protected static void addChildFragments() {
        if(fragments == null) {
            fragments = new ArrayList<>();
        }else if(fragments.size()>0){
            fragments.clear();
        }
//        暂时注释掉侵权的日报部分
//        fragments.add(new DailyFragment());
        fragments.add(new ScienceFragment());
        fragments.add(new FilmFragment());
    }

    public static LeisureFragment newInstance(){
        addChildFragments();
        return new LeisureFragment();
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(DailyDAO.TAG);
    }
}
