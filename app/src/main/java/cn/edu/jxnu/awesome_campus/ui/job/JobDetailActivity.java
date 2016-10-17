package cn.edu.jxnu.awesome_campus.ui.job;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.support.adapter.BasePagerAdapter;
import cn.edu.jxnu.awesome_campus.support.adapter.library.BookSearchResultAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.JobDetailUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailActivity extends BaseToolbarActivity {
    public static final String DETAIL_URL = "DETAIL_URL";
    public static final String JOB_DETAIL = "JOB_DETAIL";
    public static final String JOB_REQUIRE = "JOB_REQUIRE";

    private SmartTabLayout mTabLayout;
    private ViewPager mViewPager;
    private SwipeRefreshLayout mSRLayout;

    private BasePagerAdapter mPagerAdapter;

    private String detailUrl;
    private JobDetailBean jobDetailBean;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        detailUrl = getIntent().getStringExtra(DETAIL_URL);
        initToolbar();
        setToolbarTitle(getString(R.string.job_detail));
        initViews();
    }

    private void initViews() {
        mTabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.inner_viewpager);
        mSRLayout = (SwipeRefreshLayout) findViewById(R.id.srlayout);
        mTabLayout.setVisibility(View.GONE);

        initSRLayout();
    }

    private void initSRLayout() {
        mSRLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSRLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJobDetail();
            }
        });
        mSRLayout.setRefreshing(true);
        loadJobDetail();
    }

    private void loadJobDetail() {
        if (detailUrl != null) {
            JobDetailUtil.loadJobDetail(detailUrl);
        } else {
            mSRLayout.setRefreshing(false);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventComing(EventModel eventModel) {
        mSRLayout.setRefreshing(false);
        switch (eventModel.getEventCode()) {
            case EVENT.GET_JOB_DETAIL_SUCCESS:
                jobDetailBean = (JobDetailBean) eventModel.getDataList().get(0);
                setupFragments();
                break;
            case EVENT.GET_JOB_DETAIL_FAIL:
                break;
            case EVENT.NONE_JOB_DETAIL:
                break;
            default:
                break;
        }
    }

    private void setupFragments() {
        JobDetailFragment jobDetailFragment = setupJobDetail();
        JobRequirementsFragment jobRequirementsFragment = setupJobRequirements();
        List<BaseFragment> fragmentList = Arrays.asList(new BaseFragment[]{jobDetailFragment, jobRequirementsFragment});
        mTabLayout.setVisibility(View.VISIBLE);
        mPagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        mSRLayout.setEnabled(false);
    }

    private JobDetailFragment setupJobDetail() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(JOB_DETAIL, jobDetailBean);
        JobDetailFragment jobDetailFragment = new JobDetailFragment();
        jobDetailFragment.setArguments(bundle);
        return jobDetailFragment;
    }

    private JobRequirementsFragment setupJobRequirements() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(JOB_REQUIRE, jobDetailBean.getId());
        JobRequirementsFragment jobRequirementsFragment = new JobRequirementsFragment();
        jobRequirementsFragment.setArguments(bundle);
        return jobRequirementsFragment;
    }

    public static void launchActivity(Context context, String detailUrl) {
        Intent intent = new Intent();
        intent.putExtra(DETAIL_URL, detailUrl);
        intent.setClass(context, JobDetailActivity.class);
        context.startActivity(intent);
    }
}
