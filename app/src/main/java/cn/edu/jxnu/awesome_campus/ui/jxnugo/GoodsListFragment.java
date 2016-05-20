package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.GoodsListAdapter;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.LoadingFooter;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.RecyclerViewStateUtils;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;

/**
 * Created by KevinWu on 16-5-12.
 */
public class GoodsListFragment  extends BaseListFragment {
    public static final String TAG="GoodsListFragment";
    private static int TOTAL_COUNTER;
    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 16;
    /**已经获取到多少条数据了*/
    private int mCurrentCounter = 0;
    private GoodsModel goodsModel;
    private ArrayList<GoodsModel> mList;
    private GoodsListAdapter goodsListAdapter;
    private String nexPage;

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.goodslist);
    }
    @Override
    public void onDataRefresh() {
        goodsModel.loadFromNet();
    }

    @Override
    public void bindAdapter() {}


    @Override
    public void addHeader() {}

    @Override
    public void initView() {
        goodsModel=new GoodsModel();
        mList=new ArrayList<>();
        displayLoading();
        goodsModel.loadFromCache();
    }

    private void addItems(ArrayList<GoodsModel> list) {
        for(GoodsModel g:list)
        mList.add(g);
        mCurrentCounter += list.size();
    }
    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
    }
    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);

        switch (eventModel.getEventCode()){
            case EVENT.GOODS_LIST_REFRESH_SUCCESS:
                initData(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.GOODS_LIST_REFRESH_FAILURE:
                hideLoading();
                break;
//            case EVENT.GOODS_LIST_NEXTPAGE_REFRESH_SUCCESS:
//                loadNextPage(eventModel.getDataList());
//                hideLoading();
//                break;
//            case EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE:
//                hideLoading();
//                break;
        }
    }

    /**
     * LoadNextPage DATA
     * @param tempNextList
     */
    private void loadNextPage(List<GoodsListBean> tempNextList){
        nexPage=tempNextList.get(0).getNext();

        ArrayList<GoodsModel> tempNextAL=new ArrayList<>();
        for(int i=0;i<tempNextList.get(0).getPosts().length;i++)
            tempNextAL.add(tempNextList.get(0).getPosts()[i]);
        addItems(tempNextAL);
//        Log.d("loadNextPage",nexPage);
//        Log.d("loadNextPage","当前mCurrentCounter "+mCurrentCounter);
//        Log.d(TAG,"当前TOTAL_COUNTER "+TOTAL_COUNTER);
        notifyDataSetChanged();
    }

    /**
     * FrstLoad DATA
     * @param tempList
     */
    private void initData(List<GoodsListBean> tempList) {
        mList.clear();
        GoodsModel[] g=tempList.get(0).getPosts();
        nexPage=tempList.get(0).getNext();
        TOTAL_COUNTER=tempList.get(0).getCount();
        ArrayList<GoodsModel> tempAL=new ArrayList<>();
        for(int i=0;i<g.length;i++)
            tempAL.add(g[i]);
        addItems(tempAL);
        goodsListAdapter=new GoodsListAdapter(getContext(),mList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsListAdapter);
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
        mCurrentCounter=mList.size();
    }


    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
            requestData();
        }
    };
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d(TAG, "the state is Loading, just wait..");
//                return;
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more

                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(getActivity(), recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }
        }
    };


    /**
     * 请求新数据
     */
    private void requestData() {
        final Handler handler = new Handler(Looper.getMainLooper());
//        Log.d(TAG,"请求数据");
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
//        mHandler.sendEmptyMessage(-1);
//        Log.d(TAG,"正在加载的页面为："+nexPage);
        NetManageUtil.getAuth(nexPage)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .enqueue(new JsonEntityCallback<GoodsListBean>() {
                    @Override
                    public void onFailure(IOException e) {
                        Log.e(TAG,e.toString());
//                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE));
                    }

                    @Override
                    public void onSuccess(GoodsListBean entity, Headers headers) {
                        if (entity != null) {
                            final List<GoodsListBean> list = Arrays.asList(entity);
//                            loadNextPage(list);
//                            Log.d(TAG,"当前mCurrentCounter "+mCurrentCounter);
//                            Log.d(TAG,"当前TOTAL_COUNTER "+TOTAL_COUNTER);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
//                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_SUCCESS, list));
                                    loadNextPage(list);
                                }
                            });
                        } else {
//                            EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_NEXTPAGE_REFRESH_FAILURE));
                        }
                    }
                });
    }

}
