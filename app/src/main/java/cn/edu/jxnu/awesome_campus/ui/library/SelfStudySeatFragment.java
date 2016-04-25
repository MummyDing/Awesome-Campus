package cn.edu.jxnu.awesome_campus.ui.library;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.adapter.library.SelfStudySeatsAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.SelfStudyRoomLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

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
        if (EducationLoginUtil.isLogin()){
            SelfStudyRoomLoginUtil.onLogin(EducationLoginUtil.getStudentID());
            setOnLineLayout(true);
        }else {
            setOnLineLayout(false);
        }

    }


    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){

            case EVENT.SELFSTUDYROOM_LOGIN_SUCCESS:
                model.setCookie((String) eventModel.getData());
                model.loadFromNet();
                break;
            case EVENT.SELF_STUDY_SEATS_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.SELFSTUDYROOM_LOGIN_FAILURE:
            case EVENT.SELFSTUDYROOM_LOGIN_FAILURE_NETWORKERROR:
            case EVENT.SELF_STUDY_SEATS_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;

        }
    }
}
