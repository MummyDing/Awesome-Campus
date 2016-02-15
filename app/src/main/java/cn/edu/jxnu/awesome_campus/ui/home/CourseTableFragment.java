package cn.edu.jxnu.awesome_campus.ui.home;

import android.util.Log;
import android.widget.Toast;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.home.CourseTableAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
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
    private CourseTableModel model;

    private List<CourseTableModel> weekCourse;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.course_table);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new CourseTableModel();
        adapter = new CourseTableAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
        model.loadFromNet();
        displayLoading();
    }

    @Override
    public void addHeader() {
        /**
         *非正式代码，待完善
         */
        WeekSpinnerWrapper spinnerWrapper = new WeekSpinnerWrapper(getContext());
        spinnerWrapper.setIndex(TimeUtil.getDayOfWeek()-1);
        spinnerWrapper.setOnDayChangedListener(new OnDayChangedListener() {
            @Override
            public void onDayChanged(int day) {
                currentSelectedDay = day;
                if(weekCourse != null) {
                    adapter.newList(weekCourse);
                }
            }
        });
        headerLayout.addView(spinnerWrapper.build());
    }

    @Override
    public void initView() {
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
        }
    }
}
