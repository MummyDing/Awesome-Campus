package cn.edu.jxnu.awesome_campus.support.adapter.education;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.education.ExamTimeModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-2-16.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ExamTimeAdapter extends BaseListAdapter<ExamTimeModel,ExamTimeAdapter.VH> {


    public ExamTimeAdapter(Context mContext, ExamTimeModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_exam,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        ExamTimeModel model = getItem(position);
        holder.courseName.setText("Course Name: "+model.getCourseName());
        holder.examTime.setText("Exam Time : "+model.getExamTime());
        holder.examRoom.setText("Exam Room : "+model.getExamRoom());
        holder.examSeat.setText("Exam Seat :  "+model.getExamSeat());
        holder.examRemark.setText("Remark :   "+model.getRemark());
    }

    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView courseName;
        TextView examTime;
        TextView examRoom;
        TextView examSeat;
        TextView examRemark;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            examTime = (TextView) itemView.findViewById(R.id.examTime);
            examRoom = (TextView) itemView.findViewById(R.id.examRoom);
            examSeat = (TextView) itemView.findViewById(R.id.examSeat);
            examRemark = (TextView) itemView.findViewById(R.id.examRemark);
        }
    }
}
