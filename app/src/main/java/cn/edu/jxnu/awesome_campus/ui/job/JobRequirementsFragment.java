package cn.edu.jxnu.awesome_campus.ui.job;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.model.job.JobRequirementModel;
import cn.edu.jxnu.awesome_campus.support.adapter.job.JobRequirementListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.job.JobDetailUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobRequirementsFragment extends BaseFragment {
    private static final String TAG = JobRequirementsFragment.class.getName();

    private SwipeRefreshLayout mSRLayout;
    private RecyclerView mRequirementsRV;
    private JobRequirementListAdapter mAdapter;

    private String detailUrl;
    private JobRequirementModel[] jobRequirementModels;

    @Override
    protected void init() {
        getParams();

        mSRLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.srlayout);
        mRequirementsRV = (RecyclerView) parentView.findViewById(R.id.job_requirements_RV);
        mRequirementsRV.setVisibility(View.GONE);
        initRecyclerView();

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
    }

    private void loadJobDetail() {
        if (detailUrl != null) {
            JobDetailUtil.loadJobDetail(detailUrl);
        } else {
            mSRLayout.setRefreshing(false);
        }
    }

    private void initRecyclerView() {
        mAdapter = new JobRequirementListAdapter(getContext(), null);
        mRequirementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mRequirementsRV.setAdapter(mAdapter);
    }

    private void getParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            detailUrl = bundle.getString(JobDetailActivity.DETAIL_URL);
        }
    }

    @Override
    public String getTitle() {
        return InitApp.AppContext.
                getString(R.string.recruitment_detail);
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        mSRLayout.setRefreshing(false);
        switch (eventModel.getEventCode()) {
            case EVENT.GET_JOB_DETAIL_SUCCESS:
                JobDetailBean bean = (JobDetailBean) eventModel.getDataList().get(0);
                jobRequirementModels = bean.getId();
                mAdapter.newList(Arrays.asList(jobRequirementModels));
                mRequirementsRV.setVisibility(View.VISIBLE);
                mSRLayout.setEnabled(false);
                break;
            case EVENT.GET_JOB_DETAIL_FAIL:
                break;
            case EVENT.NONE_JOB_DETAIL:
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_job_requirements;
    }
}
