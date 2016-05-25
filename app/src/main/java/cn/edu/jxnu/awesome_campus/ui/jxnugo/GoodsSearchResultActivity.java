package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.tendcloud.tenddata.TCAgent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.jxnugo.CLGoodsListAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * 搜索结果activity
 * Created by KevinWu on 16-5-25.
 */
public class GoodsSearchResultActivity extends BaseToolbarActivity{
    public static final String TAG="G.S.S.R.Activity";
    private String title="";//toolbar的标题
    private String keyWord;
    private GoodsModel model;
    private ProgressBar progressBar;
    private BaseListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search_result);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        EventBus.getDefault().register(this);
        initView();
        bindAdapter();
        initToolbar();
        setToolbarTitle(title);
        onDataRefresh();
    }


    /**
     * 加载搜索结果
     */
    private void onDataRefresh() {

    }
    private void bindAdapter() {
        model=new GoodsModel();
        adapter=new CLGoodsListAdapter(this,model);
        recyclerView.setAdapter(adapter);
        displayLoading();
    }

    private void displayLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void initView() {
        progressBar=(ProgressBar)findViewById(R.id.progressbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        switch (eventModel.getEventCode()){


        }
    }
}
