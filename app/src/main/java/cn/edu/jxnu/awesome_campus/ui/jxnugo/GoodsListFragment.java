package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.GoodsListAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by KevinWu on 16-5-12.
 */
public class GoodsListFragment  extends BaseListFragment {
    private GoodsModel goodsModel;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.goodslist);
    }
    @Override
    public void onDataRefresh() {
        goodsModel.loadFromNet();
    }
    @Override
    public void bindAdapter() {
        goodsModel=new GoodsModel();
        adapter=new GoodsListAdapter(getContext(),goodsModel);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {
        goodsModel.loadFromCache();
    }

    private static final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.GOODS_LIST_REFRESH_SUCCESS:
                List list = eventModel.getDataList();
                adapter.newList(list);
                hideLoading();
                break;
            case EVENT.GOODS_LIST_REFRESH_FAILURE:
                hideLoading();
                break;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
//        NetManageUtil.cancelByTag(GoodsDAO.TAG);
    }
}
