package cn.edu.jxnu.awesome_campus.ui.leisure;

import android.os.Handler;
import android.os.Looper;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.leisure.FilmDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.Settings;
import cn.edu.jxnu.awesome_campus.support.adapter.leisure.FilmAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmFragment extends BaseListFragment {

    private FilmModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.film);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new FilmModel();
        adapter = new FilmAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
       // onDataRefresh();
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
            case EVENT.FILM_LOAD_CACHE_SUCCESS:
                if (Settings.autoRefresh){
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onDataRefresh();
                        }
                    },1500);
                }
            case EVENT.FILM_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.FILM_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
            case EVENT.FILM_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(FilmDAO.TAG);
    }
}
