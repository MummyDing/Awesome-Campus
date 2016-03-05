package cn.edu.jxnu.awesome_campus.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.opengl.Visibility;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import org.greenrobot.eventbus.EventBus;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseBean;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWidgetActivity;

/**
 * Created by KevinWu on 16-3-5.
 */
public class CourseTable4x2 extends BaseWidgetActivity {
    public static final String TAG = "CourseTable4x2";
    private Context mContext;
    private static final String REFRESH = "CourseTable4x2Reflash";
    private boolean run = true;
    private static int REFRESH_TIME=3600;
    private CourseTableModel courseTableModel;
    private List<CourseTableModel> weekCourse;
    BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK)) {
                mContext.sendBroadcast(new Intent(REFRESH));
            }
        }
    };

    Thread myThread = new
            Thread() {
                public void run() {
                    while (run) {
                        try {
                            Thread.sleep(REFRESH_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.d("执行课程表小部件更新方法", "更新中");
                        mContext.sendBroadcast(new Intent(REFRESH));
                    }
                }
            };

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                break;
            case EVENT.COURSE_TABLE_LOAD_CACHE_SUCCESS:
                weekCourse = eventModel.getDataList();
                Log.d("ceshi", "--" + weekCourse.get(0).getCourseList().size());
                updateView();
                break;
        }
    }

    private void updateView() {
        RemoteViews rviews = new RemoteViews(mContext.getPackageName(), R.layout.widget_4x2_course_table);
        rviews.setTextViewText(R.id.week,TimeUtil.getWeekString());

        List<CourseBean> courseList=weekCourse.get(TimeUtil.getDayOfWeek()).getCourseList();
        if(courseList.size()==0){
            //当天没课
            rviews.setViewVisibility(R.id.nowCourse,View.GONE);
            rviews.setViewVisibility(R.id.noCourse,View.VISIBLE);
            rviews.setTextViewText(R.id.noCourseInfo,mContext.getString(R.string.no_course_today));
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
                rviews.setTextViewText(R.id.noCourseInfo,mContext.getString(R.string.all_courses_are_over));
            }
            else{
                rviews.setViewVisibility(R.id.nowCourse,View.VISIBLE);
                rviews.setViewVisibility(R.id.noCourse,View.GONE);
                rviews.setTextViewText(R.id.courseTime, TimeUtil.getCourseArea(weekCourse.get(TimeUtil.getDayOfWeek()).getCourseList().get(nowPos).getCourseOfDay()));
                rviews.setTextViewText(R.id.courseName,weekCourse.get(TimeUtil.getDayOfWeek()).getCourseList().get(nowPos).getCourseName());
                rviews.setTextViewText(R.id.roomNum,weekCourse.get(TimeUtil.getDayOfWeek()).getCourseList().get(nowPos).getCourseRoom());
            }
        }
        AppWidgetManager.getInstance(mContext).updateAppWidget(new ComponentName(mContext, CourseTable4x2.class), rviews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        mContext = context;
        Log.d("执行课程表小部件更新方法", "--");
        EventBus.getDefault().register(this);
        myThread.start();
        context.getApplicationContext().registerReceiver(myBroadcast, new IntentFilter(Intent.ACTION_TIME_TICK));
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        courseTableModel = new CourseTableModel();
        mContext=context;
        Log.d(TAG, "调用onReceive");
        String action = intent.getAction();
        Log.d(TAG, "动作为：" + action);
        if (REFRESH.equals(action)) {
            courseTableModel.loadFromCache();
        } else if (Intent.ACTION_TIME_TICK.equals(action)) {
            courseTableModel.loadFromCache();
        }
        super.onReceive(context, intent);
    }


    public void onDisabled(Context context) {
        Log.d(TAG, "调用onDisabled");
        super.onDisabled(context);
        run = false;
    }
}



