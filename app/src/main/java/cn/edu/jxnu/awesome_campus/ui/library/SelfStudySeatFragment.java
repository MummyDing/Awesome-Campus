package cn.edu.jxnu.awesome_campus.ui.library;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.library.SelfStudySeatsAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.ui.login.StudyLoginFragment;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SelfStudySeatFragment extends BaseListFragment{

    private SelfStudySeatLeftModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.seats);
    }

    @Override
    public void bindAdapter() {
        model = new SelfStudySeatLeftModel();
        adapter = new SelfStudySeatsAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDataRefresh() {
        super.onDataRefresh();
        model.loadFromNet();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {


        // 判断是否登陆
        if (SelfStudyRoomLoginUtil.isLogin()){
            model.loadFromNet();
            setOnLineLayout(true);
        }else {
            setOnLineLayout(false);
        }

    }


    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){

            case EVENT.SELF_STUDY_SEATS_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.SELF_STUDY_SEATS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }
}
