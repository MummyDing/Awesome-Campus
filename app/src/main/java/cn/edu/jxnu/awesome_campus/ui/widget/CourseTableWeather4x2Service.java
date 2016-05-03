package cn.edu.jxnu.awesome_campus.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseBean;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.weather.WeatherConfig;
import cn.edu.jxnu.awesome_campus.ui.base.BaseWidgetService;

/**
 * 有待重构，代码太臃肿，可读性差
 * Created by KevinWu on 16-5-1.
 */
public class CourseTableWeather4x2Service extends BaseWidgetService {
    private CourseTableModel courseTableModel = null;
    private WeatherInfoModel weatherInfoModel=null;
    private List<WeatherInfoModel> weatherInfoModelList;
    private List<CourseTableModel> weekCourse;
    private Context context;
    private int pos = 0;
    RemoteViews rviews;
    List<CourseBean> courseList;
    private int nowWeek;
    private static final String TAG="CourseTableWeather4x2Service";



    @Override
    protected void init() {
        context = this;
        courseTableModel = new CourseTableModel();
        courseTableModel.loadFromCache();
        courseList=new ArrayList<>();
        weatherInfoModel=new WeatherInfoModel();
        weatherInfoModel.loadFromCache();
        rviews = new RemoteViews(context.getPackageName(), R.layout.widget_4x2_course_table_weather);
        Log.d("初始化小部件服务，类为：", "--" + this.getClass());
    }

    @Override
    protected void updateWidget() {
        //更新逻辑暂时放到两个单独的方法里
    }

    private void changeWidget() {
        Log.d("CourseList大小：","-- "+courseList.size());
        if (pos == courseList.size()) {
            //当天的课全部上完了
            rviews.setViewVisibility(R.id.nowCourse, View.GONE);
            rviews.setViewVisibility(R.id.noCourse, View.VISIBLE);
            rviews.setTextViewText(R.id.noCourseInfo, context.getString(R.string.all_courses_are_over));
        } else {
            rviews.setViewVisibility(R.id.nowCourse, View.VISIBLE);
            rviews.setViewVisibility(R.id.noCourse, View.GONE);
            rviews.setTextViewText(R.id.courseTime, TimeUtil.getCourseArea(weekCourse.get(nowWeek).getCourseList().get(pos).getCourseOfDay()));
            rviews.setTextViewText(R.id.courseName, weekCourse.get(nowWeek).getCourseList().get(pos).getCourseName());
            rviews.setTextViewText(R.id.roomNum, weekCourse.get(nowWeek).getCourseList().get(pos).getCourseRoom());
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTableWeather4x2Privider.class), rviews);
    }

    @Override
    protected void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()) {
            case EVENT.COURSE_TABLE_LOAD_CACHE_SUCCESS:
                weekCourse = eventModel.getDataList();
                updateCourse();
                break;
            case EVENT.WEATHER_INFO_LOAD_CACHE_SUCCESS:
                Log.d("更新小部件天气","---");
                weatherInfoModelList=eventModel.getDataList();
                updateWeather();
                break;
            case EVENT.WEATHER_INFO_LOAD_CACHE_FAILURE:
                needRefreshWeather();
                break;
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                //给出先登录提示
                needLoginInfo();
                break;
        }
    }

    /**
     * 更新天气
     */
    private void updateWeather() {
        weatherInfoModel=weatherInfoModelList.get(0);
        Log.d("取得时间为：","--"+TimeUtil.getHourMinute());
        int nowTime=Integer.parseInt(weatherInfoModel.getInfo().getNight()[5].substring(0, 2)+weatherInfoModel.getInfo().getNight()[5].substring(3, 5));
        if(TimeUtil.getHourMinute()<nowTime){
            rviews.setImageViewResource(R.id.weatherIcon,
                    WeatherConfig.WeatherPic
                            [
                            WeatherConfig.getWeatherPicNum(weatherInfoModel.getInfo().getDay()[1])
                            ]);
            rviews.setTextViewText(R.id.weatherInfo,
                    weatherInfoModel.getInfo().getDay()[1]+"  "+
                            weatherInfoModel.getInfo().getDay()[2] + "°C ~ "
                            +weatherInfoModel.getInfo().getNight()[2] + "°C");
        }else{
            rviews.setImageViewResource(R.id.weatherIcon,
                    WeatherConfig.WeatherPic
                            [
                            WeatherConfig.getWeatherPicNum(weatherInfoModel.getInfo().getNight()[1])
                            ]);
            rviews.setTextViewText(R.id.weatherInfo,
                    weatherInfoModel.getInfo().getNight()[1]+"  "+
                            weatherInfoModel.getInfo().getDay()[2] + "°C ~ "
                            +weatherInfoModel.getInfo().getNight()[2] + "°C");
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTableWeather4x2Privider.class), rviews);

    }

    /**
     * 更新课程表
     */
    private void updateCourse() {
        //更新课表后再更新天气
        Log.d("更新控件", "--课程表天气4x2");
        courseList.clear();
        nowWeek = TimeUtil.getDayOfWeek();
        nowWeek = nowWeek - 1;
        courseList = weekCourse.get(nowWeek).getCourseList();

        if (courseList.size() == 0) {
            //当天没课
            rviews.setViewVisibility(R.id.nowCourse, View.GONE);
            rviews.setViewVisibility(R.id.noCourse, View.VISIBLE);
            rviews.setTextViewText(R.id.noCourseInfo, context.getString(R.string.no_course_today));
            AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, CourseTableWeather4x2Privider.class), rviews);
        } else {
            for (int i = 0; i < courseList.size(); i++) {
                int nowCourseHour = Integer.parseInt(TimeUtil.getCourseArea(courseList.get(i).getCourseOfDay()).substring(0, 2) + TimeUtil.getCourseArea(courseList.get(i).getCourseOfDay()).substring(3, 5));
                if (TimeUtil.getHourMinute() <= nowCourseHour) {
                    pos = i;//显示下一节要上的课
                    break;
                } else {
                    pos = courseList.size();
                }
            }
            changeWidget();
        }
    }

    private void needRefreshWeather() {
    }

    private void needLoginInfo() {
    }

}
