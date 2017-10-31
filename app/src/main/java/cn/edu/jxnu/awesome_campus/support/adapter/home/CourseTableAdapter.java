package cn.edu.jxnu.awesome_campus.support.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseBean;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.ui.home.CourseDetailsDialog;
import cn.edu.jxnu.awesome_campus.ui.home.CourseTableFragment;

/**
 * Created by MummyDing on 16-2-3.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableAdapter extends BaseListAdapter<CourseTableModel,CourseTableAdapter.VH> {


    private List<CourseBean> listCourse;
    private List<CourseInfoModel> courseInfoList = new ArrayList<>();

    public CourseTableAdapter(Context mContext, CourseTableModel courseTableModel) {
        super(mContext, courseTableModel);
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
        final CourseBean courseBean = listCourse.get(position);
        holder.timeArea.setText(TimeUtil.getCourseArea(courseBean.getCourseOfDay()));
        holder.courseName.setText(courseBean.getCourseName());
        holder.roomNumber.setText(courseBean.getCourseRoom());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseInfoModel model = getCourseInfoModel(courseBean.getCourseName());
                if(model != null){
                    Intent intent = new Intent(mContext, CourseDetailsDialog.class);
                    EventBus.getDefault().postSticky(new EventModel<CourseInfoModel>(EVENT.SEND_MODEL_DETAIL,model));
                    mContext.startActivity(intent);
                }
            }
        });
    }

    private CourseInfoModel getCourseInfoModel(String courseName){
        for(CourseInfoModel model: courseInfoList){
            if(model.getCourseName().trim().equals(courseName.trim())){
                return model;
            }
        }
        return null;
    }


    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView timeArea;
        TextView roomNumber;
        TextView courseName;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            timeArea = (TextView) itemView.findViewById(R.id.timeArea);
            roomNumber = (TextView) itemView.findViewById(R.id.roomNum);
            courseName = (TextView) itemView.findViewById(R.id.courseName);
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

        if (listCourse.isEmpty()){
            EventBus.getDefault().post(new EventModel<Void>(EVENT.NO_COURSE));
        }else {
            EventBus.getDefault().post(new EventModel<Void>(EVENT.HAVE_COURSE));
        }
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

    public void addCourseInfoList(List<CourseInfoModel> list){
        courseInfoList.clear();
        courseInfoList.addAll(list);
    }


}
