package cn.edu.jxnu.awesome_campus.ui.about;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.support.adapter.about.NotifyAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-4-28.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyListFragment extends BaseListFragment{

    NotifyModel model;

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onDataRefresh() {
        super.onDataRefresh();
    }

    @Override
    public void bindAdapter() {
        model = new NotifyModel();
        adapter = new NotifyAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    @Override
    public void addHeader() {
        model.loadFromCache();
    }

    @Override
    public void initView() {

    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.NOTIFY_LOAD_CACHE_SUCCESS:
                List list = eventModel.getDataList();
                adapter.newList(list);
                hideLoading();
                break;

            case EVENT.NOTIFY_LOAD_CACHE_FAILURE:
                hideLoading();
                // 显示没有消息
                Snackbar.make(getView(), InitApp.AppContext.getString(R.string.no_message),Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
