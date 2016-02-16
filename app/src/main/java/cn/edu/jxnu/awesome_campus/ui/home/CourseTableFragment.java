package cn.edu.jxnu.awesome_campus.ui.home;

import android.view.View;

import org.angmarch.views.NiceSpinner;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseInfoModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.home.CourseTableAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
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
        courseTableModel.loadFromNet();
        displayLoading();
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
        spinnerWrapper.build((NiceSpinner) parentView.findViewById(R.id.spinner));
    }

    @Override
    public void initView() {
        setOnLineLayout(EducationLoginUtil.isLogin());
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        super.onEventComing(eventModel);
        switch (eventModel.getEventCode()){
            case EVENT.COURSE_TABLE_REFRESH_SUCCESS:
                weekCourse = eventModel.getDataList();
                adapter.newList(eventModel.getDataList());
                hideLoading();
                break;
            case EVENT.COURSE_TABLE_REFRESH_FAILURE:
                displayNetworkError();
                break;
            case EVENT.COURSE_INFO_REFRESH_FAILURE:
                displayNetworkError();
                break;
            case EVENT.COURSE_INFO_REFRESH_SUCCESS:
                adapter.addCourseInfoList(eventModel.getDataList());
                break;
        }
    }
}
