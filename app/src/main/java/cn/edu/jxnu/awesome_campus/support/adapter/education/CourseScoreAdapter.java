package cn.edu.jxnu.awesome_campus.support.adapter.education;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.education.ScoreDetailsDialog;

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
        final CourseScoreModel model = getItem(position);
        holder.courseName.setText(model.getCourseName());
        holder.courseScore.setText(model.getCourseScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ScoreDetailsDialog.class);
                EventBus.getDefault().postSticky(new EventModel<CourseScoreModel>(EVENT.SEND_MODEL_DETAIL,model));
                mContext.startActivity(intent);
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
            GradientDrawable myGrad=(GradientDrawable)courseScore.getBackground();
            TypedArray array = mContext.getTheme().obtainStyledAttributes(new int[] {
                    android.R.attr.colorAccent,
            });
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            myGrad.setColor(array.getColor(0,0xFFFFFF));
            array.recycle();
        }
    }
}
