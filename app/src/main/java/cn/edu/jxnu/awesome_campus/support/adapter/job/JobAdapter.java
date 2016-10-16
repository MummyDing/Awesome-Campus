package cn.edu.jxnu.awesome_campus.support.adapter.job;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.job.JobBean;
import cn.edu.jxnu.awesome_campus.support.recycle.NormalSwipeAdapter;

/**
 * Created by yzr on 16/10/16.
 */

public class JobAdapter extends NormalSwipeAdapter<JobBean> {


    public JobAdapter(List<JobBean> list) {
        super(list);
    }

    @Override
    public RecyclerView.ViewHolder createNormalViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindNormalViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

    }

    private static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
