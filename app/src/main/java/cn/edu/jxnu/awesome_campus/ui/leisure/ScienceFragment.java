package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.leisure.ScienceDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.adapter.leisure.ScienceAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceFragment extends BaseListFragment {

    private ScienceModel model;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.science);
    }

    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new ScienceModel();
        adapter = new ScienceAdapter(getActivity(),model);
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

    private static Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.SCIENCE_LOAD_CACHE_SUCCESS:
                if (Settings.autoRefresh){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onDataRefresh();
                        }
                    },1500);
                }
            case EVENT.SCIENCE_REFRESH_SUCCESS:
                List list = eventModel.getDataList();
                adapter.newList(list);
                hideLoading();
                break;
            case EVENT.SCIENCE_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
            case EVENT.SCIENCE_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(ScienceDAO.TAG);
    }
}
