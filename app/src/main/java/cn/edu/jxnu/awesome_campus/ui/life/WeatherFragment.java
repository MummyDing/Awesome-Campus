package cn.edu.jxnu.awesome_campus.ui.life;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.life.WeatherListAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by KevinWu on 16-5-1.
 */
public class WeatherFragment  extends BaseListFragment {
    private WeatherInfoModel model;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.campus_weather);//测试用
    }

    @Override
    public void bindAdapter() {
        model=new WeatherInfoModel();
        adapter=new WeatherListAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    @Override
    public void addHeader() {

    }
    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }
    @Override
    public void initView() {
        model.loadFromCache();
    }
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.WEATHER_INFO_LOAD_CACHE_SUCCESS:
            case EVENT.WEATHER_INFO_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.WEATHER_INFO_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
            case EVENT.WEATHER_INFO_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }
}
