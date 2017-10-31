package cn.edu.jxnu.awesome_campus.ui.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.view.base.BaseListView;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseListFragment extends BaseFragment implements BaseListView {

    protected RelativeLayout onLineLayout;
    protected RelativeLayout offLineLayout;
    protected FrameLayout headerLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected ProgressBar progressBar;
    protected ImageButton networkBtn;
    protected BaseListAdapter adapter;
    protected CardView spinnerCard;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected Button toLoginBtn;
    protected TextView tip;
    @Override
    protected void init() {
        onLineLayout = (RelativeLayout) parentView.findViewById(R.id.onLineLayout);
        offLineLayout = (RelativeLayout) parentView.findViewById(R.id.offLineLayout);
        headerLayout = (FrameLayout) parentView.findViewById(R.id.headerLayout);
        layoutManager = new LinearLayoutManager(InitApp.AppContext);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        networkBtn = (ImageButton) parentView.findViewById(R.id.networkBtn);
        spinnerCard = (CardView) parentView.findViewById(R.id.spinner_card_view);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        toLoginBtn = (Button) parentView.findViewById(R.id.toLoginBtn);
        tip = (TextView) parentView.findViewById(R.id.tip);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);
        networkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                onDataRefresh();
            }
        });
        toLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventModel<String>(EVENT.JUMP_TO_EDUCATION_LOGIN));
            }
        });
        bindAdapter();
        trySetupRefreshLayout();
        addHeader();
        initView();
    }

    @Override
    public void onDataRefresh() {
        if (progressBar!=null && progressBar.getVisibility()== View.VISIBLE){
            if (swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_common_list;
    }

    @Override
    public boolean trySetupRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.refreshLayout);
        if(swipeRefreshLayout == null){
            return false;
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkBtn.setVisibility(View.GONE);
                onDataRefresh();
            }
        });
        return true;
    }


    @Override
    public void displayLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);

        if(swipeRefreshLayout != null){
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void displayNetworkError() {
        if (adapter.getItemCount() == 0) {
            networkBtn.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onEventComing(EventModel eventModel) {
        // 暂时不重写子类
        // 在这里就可以根据EventCode进行相应的处理
    }

    protected void setOnLineLayout(boolean flag){
        if(flag){
            onLineLayout.setVisibility(View.VISIBLE);
            offLineLayout.setVisibility(View.GONE);
            displayLoading();
        }else{
            onLineLayout.setVisibility(View.GONE);
            offLineLayout.setVisibility(View.VISIBLE);
            tip.setVisibility(View.GONE);
        }
    }
}
