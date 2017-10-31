package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cundong.recyclerview.EndlessRecyclerOnScrollListener;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
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
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.LoadGoodsListUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.RecyclerViewStateUtils;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * 搜索结果activity
 * Created by KevinWu on 16-5-25.
 */
public class GoodsSearchResultActivity extends BaseToolbarActivity{
    public static final String TAG="G.S.S.R.Activity";
    private String title="商品搜索结果";//toolbar的标题
    private String keyWord;
    private static int TOTAL_COUNTER;
    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 16;
    /**已经获取到多少条数据了*/
    private int mCurrentCounter = 0;
    private ProgressBar progressBar;
    private GoodsModel goodsModel;
    private ArrayList<GoodsModel> mList;
    private GoodsListAdapter goodsListAdapter;
    private RecyclerView recyclerView;
    private TextView tips;
    private String nexPage;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search_result);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        EventBus.getDefault().register(this);
        keyWord=getIntent().getStringExtra("key");
        initView();
        initToolbar();
        setToolbarTitle(title);
        bindAdapter();
        onDataRefresh();
    }


    /**
     * 加载搜索结果
     */
    private void onDataRefresh() {

    }
    private void bindAdapter() {
        goodsModel=new GoodsModel();
        mList=new ArrayList<>();
        if(keyWord!=null&&!keyWord.equals("")){
            LoadGoodsListUtil.searchByKey(this,keyWord);
            displayLoading();
        }

    }

    private void addItems(ArrayList<GoodsModel> list) {
        for(GoodsModel g:list)
            mList.add(g);
        mCurrentCounter += list.size();
    }

    private void notifyDataSetChanged() {
        mHeaderAndFooterRecyclerViewAdapter.notifyDataSetChanged();
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
        notifyDataSetChanged();
    }
    /**
     * FirstLoad DATA
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
        goodsListAdapter=new GoodsListAdapter(this,mList);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(goodsListAdapter);
        recyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        recyclerView.addOnScrollListener(mOnScrollListener);
        mCurrentCounter=mList.size();
    }
    private void displayLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initView() {
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(InitApp.AppContext,2);
        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tips=(TextView)findViewById(R.id.tips);
    }
    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.JXNUGO_SEARCH_GOODS_SUCCESS:
                List<GoodsListBean> list=(List<GoodsListBean>)eventModel.getDataList();
                Log.d(TAG,"取得的结果条数："+list.get(0).getCount());
                if(list.get(0).getCount()>0){
                    initData(eventModel.getDataList());
                }
                else{
                    tips.setVisibility(View.VISIBLE);
                    tips.setText("暂无包含关键字："+keyWord +" 的商品");
                }
                hideLoading();
                break;
            case EVENT.JXNUGO_SEARCH_GOODS_FAILURE:
                tips.setVisibility(View.VISIBLE);
                tips.setText("网络错误");
                hideLoading();
                break;
        }
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(recyclerView);
            if(state == LoadingFooter.State.Loading) {
                Log.d(TAG, "the state is Loading, just wait..");
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(GoodsSearchResultActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.Loading, null);
                requestData();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(GoodsSearchResultActivity.this, recyclerView, REQUEST_COUNT, LoadingFooter.State.TheEnd, null);
            }


        }
    };
    /**
     * 请求新数据
     */
    private void requestData() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(this);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
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
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    loadNextPage(list);
                                }
                            });
                        }
                    }
                });
    }
}
