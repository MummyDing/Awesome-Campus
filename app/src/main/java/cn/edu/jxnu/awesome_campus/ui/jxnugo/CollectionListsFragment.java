package cn.edu.jxnu.awesome_campus.ui.jxnugo;


import android.os.Handler;
import android.os.Looper;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCollectionListBean;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.CLGoodsListAdapter;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.LodingGoodsListUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by KevinWu on 16-5-17.
 */
public class CollectionListsFragment  extends BaseListFragment {
    private static final String title="收藏";
    private GoodsModel model;
    private int userId;

    @Override
    public String getTitle() {
        return title;
    }
    @Override
    public void onDataRefresh() {
        LodingGoodsListUtil.getGoodsList(InitApp.AppContext,userId);
    }
    @Override
    public void bindAdapter() {
        model = new GoodsModel();
        adapter = new CLGoodsListAdapter(getContext(),model);
        recyclerView.setAdapter(adapter);
        SPUtil sp=new SPUtil(InitApp.AppContext);
        userId=sp.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        displayLoading();
    }

    @Override
    public void addHeader() {

    }

    @Override
    public void initView() {
        LodingGoodsListUtil.getGoodsList(InitApp.AppContext,userId);
    }
    private static final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.USERCOLLECT_REFRESH_SUCCESS:
                List<UserCollectionListBean> list = (List<UserCollectionListBean>)eventModel.getDataList();
                adapter.newList(Arrays.asList(list.get(0).getCollectionPost()));
                hideLoading();
                break;
            case EVENT.USERCOLLECT_REFRESH_FAILURE:
                hideLoading();
                break;
        }
    }
}