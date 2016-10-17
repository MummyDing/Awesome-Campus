package cn.edu.jxnu.awesome_campus.ui.job;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.Arrays;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobRequirementModel;
import cn.edu.jxnu.awesome_campus.support.adapter.job.JobRequirementListAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobRequirementsFragment extends BaseFragment {
    private static final String TAG = JobRequirementsFragment.class.getName();

    private RecyclerView mRequirementsRV;
    private JobRequirementListAdapter mAdapter;

    private JobRequirementModel[] jobRequirementModels;

    @Override
    protected void init() {
        getParams();

        mRequirementsRV = (RecyclerView) parentView.findViewById(R.id.job_requirements_RV);

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.i(TAG, "requirements size = " + jobRequirementModels.length);
        mAdapter = new JobRequirementListAdapter(getContext(), jobRequirementModels[0]);
        mRequirementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mRequirementsRV.setAdapter(mAdapter);
        mAdapter.newList(Arrays.asList(jobRequirementModels));
    }

    private void getParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            jobRequirementModels = (JobRequirementModel[]) bundle.getParcelableArray(JobDetailActivity.JOB_REQUIRE);
        }
    }

    @Override
    public String getTitle() {
        return "岗位需求";
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_job_requirements;
    }
}
