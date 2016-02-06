package cn.edu.jxnu.awesome_campus.ui.base;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.view.base.BaseListView;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public abstract class BaseListFragment extends BaseFragment implements BaseListView {

    protected FrameLayout headerLayout;
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected ProgressBar progressBar;
    protected ImageButton networkBtn;
    @Override
    protected void init() {
        headerLayout = (FrameLayout) parentView.findViewById(R.id.headerLayout);
        layoutManager = new LinearLayoutManager(InitApp.AppContext);
        progressBar = (ProgressBar) parentView.findViewById(R.id.progressBar);
        networkBtn = (ImageButton) parentView.findViewById(R.id.networkBtn);
        recyclerView = (RecyclerView) parentView.findViewById(R.id.recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        networkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkBtn.setVisibility(View.GONE);
                onNetworkBtnClick();
            }
        });

        addHeader();
        initView();
    }


    protected abstract void onNetworkBtnClick();
    @Override
    protected int getLayoutID() {
        return R.layout.layout_common_list;
    }

    @Override
    public boolean trySetupRefreshLayout() {
        return false;
    }

    @Override
    public boolean trySetupSmartTabLayout() {
        return false;
    }

    @Override
    public void displayLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayNetworkError() {
        networkBtn.setVisibility(View.VISIBLE);
    }




}
