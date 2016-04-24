package cn.edu.jxnu.awesome_campus.ui.life;

import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.life.CampusATMDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.life.CampusATMModel;
import cn.edu.jxnu.awesome_campus.support.adapter.life.CampusATMAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.life.CampusExpressAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ATMFragment extends BaseListFragment{

    private CampusATMModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.atm);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new CampusATMModel();
        adapter = new CampusATMAdapter(getActivity(),model);
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

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.CAMPUS_ATM_LOAD_CACHE_SUCCESS:
            case EVENT.CAMPUS_ATM_REFRESH_SUCCESS:
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.CAMPUS_ATM_REFRESH_FAILURE:
                hideLoading();
                displayNetworkError();
                break;
            case EVENT.CAMPUS_ATM_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(CampusATMDAO.TAG);
    }
}
