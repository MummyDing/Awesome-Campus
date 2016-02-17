package cn.edu.jxnu.awesome_campus.support.adapter.education;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.jxnu.awesome_campus.R;
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
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_course_score,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        CourseScoreModel model = getItem(position);
        holder.courseName.setText(model.getCourseName());
        holder.courseScore.setText(model.getCourseScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 待完善
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView courseName;
        TextView courseScore;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseScore = (TextView) itemView.findViewById(R.id.courseScore);
        }
    }
}
