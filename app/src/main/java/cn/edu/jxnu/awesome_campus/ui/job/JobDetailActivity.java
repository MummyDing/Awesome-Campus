package cn.edu.jxnu.awesome_campus.ui.job;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.support.adapter.BasePagerAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.job.JobDetailUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseActivity;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailActivity extends BaseActivity {
    public static final String DETAIL_URL = "DETAIL_URL";

    private Toolbar toolbar;
    private SmartTabLayout mTabLayout;
    private ViewPager mViewPager;

    private BasePagerAdapter mPagerAdapter;

    private String detailUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        detailUrl = getIntent().getStringExtra(DETAIL_URL);
        initToolbar();
        setToolbarTitle(getString(R.string.job_detail));
        initViews();
    }

    protected void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TypedArray array = getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary,
        });
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            toolbar.setBackgroundColor(array.getColor(0,0xFFFFFF));
        else
            toolbar.setBackgroundColor(Color.BLACK);
        array.recycle();
    }
    public void setToolbarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupFragments();
    }

    private void initViews() {
        mTabLayout = (SmartTabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.inner_viewpager);
    }

    private void setupFragments() {
        List<BaseFragment> fragmentList = new ArrayList<>();

        JobDetailFragment jobDetailFragment = setupJobDetail();
        JobRequirementsFragment jobRequirementsFragment = setupJobRequirements();
        fragmentList.add(jobDetailFragment);
        fragmentList.add(jobRequirementsFragment);

        mTabLayout.setVisibility(View.VISIBLE);
        mPagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setViewPager(mViewPager);


    }

    private JobDetailFragment setupJobDetail() {
        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_URL, detailUrl);
        JobDetailFragment jobDetailFragment = new JobDetailFragment();
        jobDetailFragment.setArguments(bundle);
        return jobDetailFragment;
    }

    private JobRequirementsFragment setupJobRequirements() {
        Bundle bundle = new Bundle();
        bundle.putString(DETAIL_URL, detailUrl);
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
