package cn.edu.jxnu.awesome_campus.ui.job;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.ui.base.BaseFragment;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailFragment extends BaseFragment {
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

    private JobDetailBean jobDetailBean;

    private void getParams() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            jobDetailBean = bundle.getParcelable(JobDetailActivity.JOB_DETAIL);
        }
    }

    @Override
    protected void init() {
        getParams();

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
        mIntroTV.setText(introduction);
        mProcessTV.setText(jobDetailBean.getProcess());
        mAttentionTV.setText(jobDetailBean.getAttention());
    }

    @Override
    public String getTitle() {
        return "招聘详情";
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_job_detail;
    }
}
