package cn.edu.jxnu.awesome_campus.support.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableAdapter extends BaseListAdapter<CourseTableModel,CourseTableAdapter.VH> {


    public CourseTableAdapter(Context mContext, CourseTableModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_course_table,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        CourseTableModel model = getItem(position);
    }

    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView timeArea;
        TextView roomNumber;
        TextView courseName;
        TextView teacherName;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            timeArea = (TextView) itemView.findViewById(R.id.timeArea);
            roomNumber = (TextView) itemView.findViewById(R.id.roomNum);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
            teacherName = (TextView) itemView.findViewById(R.id.teacherName);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
