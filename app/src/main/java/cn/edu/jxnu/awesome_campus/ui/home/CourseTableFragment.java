package cn.edu.jxnu.awesome_campus.ui.home;

import android.util.Log;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;


import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.home.CourseTableDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.home.CourseTableAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.OnDayChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.WeekSpinnerWrapper;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableFragment extends BaseListFragment{

    public static int currentSelectedDay = -1;
    private CourseTableModel courseTableModel;
    private CourseInfoModel courseInfoModel;
    private List<CourseTableModel> weekCourse;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.course_table);
    }


    @Override
    public void onDataRefresh() {
        courseTableModel.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        courseTableModel = new CourseTableModel();
        courseInfoModel = new CourseInfoModel();
        adapter = new CourseTableAdapter(getActivity(), courseTableModel);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addHeader() {
        spinnerCard.setVisibility(View.VISIBLE);
        WeekSpinnerWrapper spinnerWrapper = new WeekSpinnerWrapper();
        spinnerWrapper.setOnDayChangedListener(new OnDayChangedListener() {
            @Override
            public void onDayChanged(int day) {
                currentSelectedDay = day;
                if(weekCourse != null) {
                    adapter.newList(weekCourse);
                }
            }
        });
        spinnerWrapper.build((MaterialSpinner) parentView.findViewById(R.id.spinner));
    }

    @Override
    public void initView() {

        if (EducationLoginUtil.isLogin()){
            setOnLineLayout(true);
            courseTableModel.loadFromCache();
            tip.setText(InitApp.AppContext.getString(R.string.notify_no_course));
        }else {
            setOnLineLayout(false);
        }
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.COURSE_TABLE_REQUEST_REFRESH:
                displayLoading();
                onDataRefresh();
                break;
            case EVENT.COURSE_TABLE_REFRESH_SUCCESS:
                courseInfoModel.loadFromNet();
                weekCourse = eventModel.getDataList();
                adapter.newList(eventModel.getDataList());
                break;
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                hideLoading();
                break;
            case EVENT.COURSE_TABLE_LOAD_CACHE_SUCCESS:
                Log.e("---","COURSE_TABLE_LOAD_CACHE_SUCCESS");
                weekCourse = eventModel.getDataList();
                adapter.newList(eventModel.getDataList());
                courseInfoModel.loadFromCache();
                break;
            case EVENT.COURSE_TABLE_LOAD_CACHE_FAILURE:
                Log.e("---","COURSE_TABLE_LOAD_CACHE_FAILURE");
                onDataRefresh();
                break;

            case EVENT.COURSE_INFO_REFRESH_FAILURE:
                displayNetworkError();
                break;
            case EVENT.COURSE_INFO_REFRESH_SUCCESS:
            case EVENT.COURSE_INFO_LOAD_CACHE_SUCCESS:
                adapter.addCourseInfoList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.COURSE_INFO_LOAD_CACHE_FAILURE:
                courseInfoModel.loadFromNet();
                break;
            case EVENT.NO_COURSE:
                tip.setVisibility(View.VISIBLE);
                break;
            case EVENT.HAVE_COURSE:
                tip.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        NetManageUtil.cancelByTag(CourseTableDAO.TAG);
    }
}
