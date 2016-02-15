package cn.edu.jxnu.awesome_campus.support.adapter.education;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-2-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreAdapter extends BaseListAdapter<CourseScoreModel,CourseScoreAdapter.VH> {

    public CourseScoreAdapter(Context mContext, CourseScoreModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {

    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    class VH extends RecyclerView.ViewHolder{

        public VH(View itemView) {
            super(itemView);
        }
    }
}
