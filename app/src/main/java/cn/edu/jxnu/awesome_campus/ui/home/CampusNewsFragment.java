package cn.edu.jxnu.awesome_campus.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.home.CampusNewsDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.adapter.home.CampusNewsAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsFragment extends BaseListFragment {

    private CampusNewsModel model;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.compus_news);
    }

    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }


    @Override
    public void bindAdapter() {
        model = new CampusNewsModel();
        adapter = new CampusNewsAdapter(getContext(),model);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {
        model.loadFromCache();
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.CAMPUS_NEWS_LOAD_CACHE_SUCCESS:
                if (Settings.autoRefresh){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onDataRefresh();
                        }
                    },1500);
                }
            case EVENT.CAMPUS_NEWS_REFRESH_SUCCESS:
                List list = eventModel.getDataList();
                Collections.sort(list);
                adapter.newList(list);
                hideLoading();
                break;
            case EVENT.CAMPUS_NEWS_REFRESH_FAILURE:
                hideLoading();
                break;
            case EVENT.CAMPUS_NEWS_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(CampusNewsDAO.TAG);
    }
}
