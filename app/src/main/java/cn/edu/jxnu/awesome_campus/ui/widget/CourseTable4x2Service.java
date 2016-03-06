package cn.edu.jxnu.awesome_campus.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private CourseTableModel courseTableModel = null;
    private List<CourseTableModel> weekCourse;
    private Context context;
    private int posChange = 0;

    @Override
    protected void init() {
        context = this;
        courseTableModel = new CourseTableModel();
        courseTableModel.loadFromCache();
        Log.d("初始化服务，类为：", "--" + this.getClass());
    }

    @Override
    protected void updateWidget() {
        Log.d("更新控件", "--课程表4x2");
        RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.widget_4x2_course_table);
        setListener(rviews);
        rviews.setTextViewText(R.id.week, TimeUtil.getWeekString());
        int nowWeek = TimeUtil.getDayOfWeek();
        nowWeek = nowWeek - 1;

        List<CourseBean> courseList = weekCourse.get(nowWeek).getCourseList();
        if (courseList.size() == 0) {
            //当天没课
            rviews.setViewVisibility(R.id.arrow_up, View.GONE);
            rviews.setViewVisibility(R.id.arrow_down, View.GONE);
            rviews.setViewVisibility(R.id.nowCourse, View.GONE);
            rviews.setViewVisibility(R.id.noCourse, View.VISIBLE);
            rviews.setTextViewText(R.id.noCourseInfo, context.getString(R.string.no_course_today));
        } else {
            if (posChange != 0) {
                int nowPos = posChange;
                if (nowPos == 0) {
                    rviews.setViewVisibility(R.id.arrow_up, View.INVISIBLE);
                }
                if (nowPos == courseList.size() - 1) {
                    rviews.setViewVisibility(R.id.arrow_down, View.INVISIBLE);
                }
                rviews.setViewVisibility(R.id.nowCourse, View.VISIBLE);
                rviews.setViewVisibility(R.id.noCourse, View.GONE);
                rviews.setTextViewText(R.id.courseTime, TimeUtil.getCourseArea(weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseOfDay()));
                rviews.setTextViewText(R.id.courseName, weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseName());
                rviews.setTextViewText(R.id.roomNum, weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseRoom());
            } else {
                int nowPos = -1000;
                for (int i = 0; i < courseList.size(); i++) {
                    int nowCourseTime = Integer.parseInt(TimeUtil.getCourseArea(courseList.get(i).getCourseOfDay()).substring(0, 2));
                    if (TimeUtil.getHour() <= nowCourseTime) {
                        nowPos = i;//显示下一节要上的课
                        break;
                    }
                }
                if (nowPos == 0) {
                    rviews.setViewVisibility(R.id.arrow_up, View.INVISIBLE);
                }
                if (nowPos == courseList.size() - 1) {
                    rviews.setViewVisibility(R.id.arrow_down, View.INVISIBLE);
                }

                if (nowPos == -1000) {
                    //当天的课全部上完了
                    rviews.setViewVisibility(R.id.nowCourse, View.GONE);
                    rviews.setViewVisibility(R.id.noCourse, View.VISIBLE);
                    rviews.setTextViewText(R.id.noCourseInfo, context.getString(R.string.all_courses_are_over));
                } else {
                    rviews.setViewVisibility(R.id.nowCourse, View.VISIBLE);
                    rviews.setViewVisibility(R.id.noCourse, View.GONE);
                    rviews.setTextViewText(R.id.courseTime, TimeUtil.getCourseArea(weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseOfDay()));
                    rviews.setTextViewText(R.id.courseName, weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseName());
                    rviews.setTextViewText(R.id.roomNum, weekCourse.get(nowWeek).getCourseList().get(nowPos).getCourseRoom());
                }
            }
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTable4x2Privider.class), rviews);
    }

    private void setListener(RemoteViews rviews) {
        rviews.setViewVisibility(R.id.arrow_up, View.VISIBLE);
        rviews.setViewVisibility(R.id.arrow_down, View.VISIBLE);
        Intent upIntent = new Intent("CourseUp");
        Intent downIntent = new Intent("CourseDown");
        PendingIntent upPIntent = PendingIntent.getBroadcast(this, 0, upIntent, 0);
        PendingIntent downPIntent = PendingIntent.getBroadcast(this, 0, downIntent, 0);
        rviews.setOnClickPendingIntent(R.id.arrow_up, upPIntent);
        rviews.setOnClickPendingIntent(R.id.arrow_down, downPIntent);
        IntentFilter filter=new IntentFilter();
        filter.addAction("CourseUp");
        filter.addAction("CourseDown");
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                //给出先登录提示
                needLoginInfo();
                break;
            case EVENT.COURSE_TABLE_LOAD_CACHE_SUCCESS:
                weekCourse = eventModel.getDataList();
                updateWidget();
                break;
        }
    }

    private void needLoginInfo() {
        RemoteViews rviews = new RemoteViews(context.getPackageName(), R.layout.widget_4x2_course_table);
        rviews.setViewVisibility(R.id.noCourse, View.VISIBLE);
        rviews.setTextViewText(R.id.noCourseInfo, context.getString(R.string.all_courses_are_over));
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTable4x2Privider.class), rviews);
    }

    BroadcastReceiver myReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Log.d("动作为：","--"+action);
            if(action.equals("CourseUp")&&posChange>=0){
                posChange--;
                updateWidget();
            }else if(action.equals("CourseDown")&&posChange>=0){
                posChange++;
                updateWidget();
            }
        }
    };
}
