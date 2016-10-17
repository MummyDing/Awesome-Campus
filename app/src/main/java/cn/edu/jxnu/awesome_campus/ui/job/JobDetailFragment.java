package cn.edu.jxnu.awesome_campus.ui.job;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.support.utils.job.JobDetailUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailFragment extends BaseFragment {
    private NestedScrollView mLayout;
    private TextView mTitleTV;
    private TextView mPublishTimeTV;
    private TextView mHolePlaceTV;
    private TextView mHoldTimeTV;
    private TextView mCompanyNameTV;
    private TextView mCompanyPropertyTV;
    private TextView mCompanyTelTV;
    private TextView mCompanyLocationTV;
    private TextView mCompanyHomepageTV;
    private TextView mManagerTV;
    private TextView mManagerTelTV;
    private TextView mRecuritmentMailTV;
    private TextView mIntroTV;
    private TextView mProcessTV;
    private TextView mAttentionTV;
    private SwipeRefreshLayout mSRLayout;

    private String detailUrl;
    private JobDetailBean jobDetailBean;

    @Override
    protected void init() {
        getParams();

        mLayout = (NestedScrollView) parentView.findViewById(R.id.job_detail_layout);
        mLayout.setVisibility(View.GONE);
        mTitleTV = (TextView) parentView.findViewById(R.id.title_TV);
        mPublishTimeTV = (TextView) parentView.findViewById(R.id.publish_time_TV);
        mHolePlaceTV = (TextView) parentView.findViewById(R.id.hold_place_TV);
        mHoldTimeTV = (TextView) parentView.findViewById(R.id.hold_time_TV);
        mCompanyNameTV = (TextView) parentView.findViewById(R.id.company_name_TV);
        mCompanyPropertyTV = (TextView) parentView.findViewById(R.id.company_property_TV);
        mCompanyTelTV = (TextView) parentView.findViewById(R.id.company_tel_TV);
        mCompanyLocationTV = (TextView) parentView.findViewById(R.id.company_location_TV);
        mCompanyHomepageTV = (TextView) parentView.findViewById(R.id.company_homepage_TV);
        mManagerTelTV = (TextView) parentView.findViewById(R.id.recruitment_manager_tel_TV);
        mManagerTV = (TextView) parentView.findViewById(R.id.recruitment_manager_TV);
        mRecuritmentMailTV = (TextView) parentView.findViewById(R.id.recruitment_email_TV);
        mIntroTV = (TextView) parentView.findViewById(R.id.company_intro_TV);
        mProcessTV = (TextView) parentView.findViewById(R.id.process_TV);
        mAttentionTV = (TextView) parentView.findViewById(R.id.attention_TV);
        mSRLayout = (SwipeRefreshLayout) parentView.findViewById(R.id.srlayout);

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
                jobDetailBean = (JobDetailBean) eventModel.getDataList().get(0);
                setupViews();
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

    private void setupViews() {
        mLayout.setVisibility(View.VISIBLE);

        Spanned introduction = Html.fromHtml(jobDetailBean.getIntroduction());
        Spanned process = Html.fromHtml(jobDetailBean.getProcess());
        Spanned attention = Html.fromHtml(jobDetailBean.getAttention());

        mTitleTV.setText(jobDetailBean.getCompany_name() + jobDetailBean.getType());
        mPublishTimeTV.setText(jobDetailBean.getPublish_time());
        mHoldTimeTV.setText(jobDetailBean.getRecruitment_time());
        mHolePlaceTV.setText(jobDetailBean.getRecruitment_place());
        mCompanyNameTV.setText(jobDetailBean.getCompany_name());
        mCompanyPropertyTV.setText(jobDetailBean.getCompany_attribute_id());
        mCompanyTelTV.setText(jobDetailBean.getPhone());
        mCompanyLocationTV.setText(jobDetailBean.getAddress());
        mCompanyHomepageTV.setText(jobDetailBean.getHomepage());
        mManagerTelTV.setText(jobDetailBean.getPrincipal_info());
        mManagerTV.setText(jobDetailBean.getPrincipal());
        mRecuritmentMailTV.setText(jobDetailBean.getEmail());
        mIntroTV.setText(introduction);
        mProcessTV.setText(process);
        mAttentionTV.setText(attention);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_job_detail;
    }
}
