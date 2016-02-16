package cn.edu.jxnu.awesome_campus.support.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.home.CourseBean;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.ui.home.CourseTableFragment;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableAdapter extends BaseListAdapter<CourseTableModel,CourseTableAdapter.VH> {


    private List<CourseBean> listCourse;
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
        CourseBean courseBean = listCourse.get(position);
        holder.timeArea.setText(TimeUtil.getCourseArea(courseBean.getCourseOfDay()));
        holder.courseName.setText(courseBean.getCourseName());
        holder.roomNumber.setText(courseBean.getCourseRoom());
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
        if(listCourse == null){
            return 0;
        }
        return listCourse.size();
    }

    private void addCourseList(List<CourseBean> list) {
        if(listCourse != null){
            listCourse.clear();
        }
        listCourse = new ArrayList<>();
        listCourse.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void newList(List<CourseTableModel> list) {
        if(list != null && list.size()>0){
            if(CourseTableFragment.currentSelectedDay == -1){
                CourseTableFragment.currentSelectedDay = TimeUtil.getDayOfWeek()-1;
            }
            addCourseList(list.get(CourseTableFragment.currentSelectedDay).getCourseList());
        }
    }


}
