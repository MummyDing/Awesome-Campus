package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.util.Log;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.adapter.leisure.DailyAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyFragment extends BaseListFragment {

    private DailyModel model;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.daily);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new DailyModel();
        adapter = new DailyAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
        onDataRefresh();
        displayLoading();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.DAILY_REFRESH_SUCCESS:
                List list = eventModel.getDataList();
                adapter.newList(list);
                hideLoading();
                break;
            case EVENT.DAILY_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
        }
    }


}
