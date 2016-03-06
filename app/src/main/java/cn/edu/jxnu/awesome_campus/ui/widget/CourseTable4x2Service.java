package cn.edu.jxnu.awesome_campus.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseBean;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWidgetService;

/**
 * Created by KevinWu on 16-3-6.
 */
public class CourseTable4x2Service extends BaseWidgetService {
    private CourseTableModel courseTableModel=null;
    private List<CourseTableModel> weekCourse;
    private Context context;

    @Override
    protected void init() {
        context=this;
        courseTableModel = new CourseTableModel();
        courseTableModel.loadFromCache();
        Log.d("初始化服务，类为：","--"+this.getClass());
    }

    @Override
    protected void updateWidget() {
        Log.d("更新控件","--课程表4x2");
        RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.widget_4x2_course_table);
        rviews.setTextViewText(R.id.week, TimeUtil.getWeekString()+TimeUtil.getTimestamp());
        int nowWeek=TimeUtil.getDayOfWeek();
        nowWeek=nowWeek-1;

        List<CourseBean> courseList=weekCourse.get(nowWeek).getCourseList();
        if(courseList.size()==0){
            //当天没课
            rviews.setViewVisibility(R.id.nowCourse, View.GONE);
            rviews.setViewVisibility(R.id.noCourse,View.VISIBLE);
            rviews.setTextViewText(R.id.noCourseInfo,context.getString(R.string.no_course_today));
        }else{
            int nowPos=10;
            for(int i=0;i<courseList.size();i++){
                int nowCourseTime=Integer.parseInt(TimeUtil.getCourseArea(courseList.get(i).getCourseOfDay()).substring(0,2));
                if(TimeUtil.getHour()<=nowCourseTime){
                    nowPos=i;//显示下一节要上的课
                    break;
                }
            }
            if(nowPos==10){
                //当天的课全部上完了
                rviews.setViewVisibility(R.id.nowCourse,View.GONE);
                rviews.setViewVisibility(R.id.noCourse,View.VISIBLE);
                rviews.setTextViewText(R.id.noCourseInfo,context.getString(R.string.all_courses_are_over));
            }
            else{
                rviews.setViewVisibility(R.id.nowCourse,View.VISIBLE);
                rviews.setViewVisibility(R.id.noCourse,View.GONE);
                rviews.setTextViewText(R.id.courseTime, TimeUtil.getCourseArea(weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseOfDay()));
                rviews.setTextViewText(R.id.courseName,weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseName());
                rviews.setTextViewText(R.id.roomNum,weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseRoom());
            }
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTable4x2Privider.class), rviews);
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                break;
            case EVENT.COURSE_TABLE_LOAD_CACHE_SUCCESS:
                weekCourse = eventModel.getDataList();
                updateWidget();
                break;
        }
    }
}
