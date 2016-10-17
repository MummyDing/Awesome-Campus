package cn.edu.jxnu.awesome_campus.support.adapter.job;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.job.JobRequireProfessionBean;
import cn.edu.jxnu.awesome_campus.model.job.JobRequirementModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobRequirementListAdapter extends BaseListAdapter<JobRequirementModel, JobRequirementListAdapter.VH> {
    private static final String TAG = JobRequirementListAdapter.class.getName();

    public JobRequirementListAdapter(Context mContext, JobRequirementModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_jobs_list, parent, false);
        VH viewHolder = new VH(mView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        JobRequirementModel data = getItem(position);
        if (data == null) {
            return;
        }
        holder.mNameTV.setText(data.getPosition_name_id());
        holder.mCountTV.setText(String.valueOf(data.getPosition_number()));
        holder.mEducationTV.setText(data.getDegree_required());
        holder.mPayTV.setText(data.getSalary_welfare());
        holder.mRequirementsTV.setText(data.getPosition_duty());
        StringBuilder builder = new StringBuilder();
        if (data.getProfessional_id() == null || data.getProfessional_id().length == 0) {

            builder.append(InitApp.AppContext.getString(R.string.no_major_limits));
        } else {
            for (int i = 0; i < data.getProfessional_id().length; i++) {
                builder.append(data.getProfessional_id()[i].getProfessional());
                if (i != data.getProfessional_id().length - 1) {
                    builder.append("  ");
                }
            }
        }
        holder.mMajorsTV.setText(builder.toString());
    }

    class VH extends RecyclerView.ViewHolder {
        public final TextView mNameTV;
        public final TextView mCountTV;
        public final TextView mMajorsTV;
        public final TextView mEducationTV;
        public final TextView mPayTV;
        public final TextView mRequirementsTV;

        public VH(View itemView) {
            super(itemView);

            mNameTV = (TextView) itemView.findViewById(R.id.job_name_TV);
            mCountTV = (TextView) itemView.findViewById(R.id.job_count_TV);
            mMajorsTV = (TextView) itemView.findViewById(R.id.majors_TV);
            mEducationTV = (TextView) itemView.findViewById(R.id.education_TV);
            mPayTV = (TextView) itemView.findViewById(R.id.pay_TV);
            mRequirementsTV = (TextView) itemView.findViewById(R.id.requirements_TV);
        }
    }
}
