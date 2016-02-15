package cn.edu.jxnu.awesome_campus.ui.home;

import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.home.CourseTableModel;
import cn.edu.jxnu.awesome_campus.support.adapter.home.CourseTableAdapter;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.OnDayChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.WeekSpinnerWrapper;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableFragment extends BaseListFragment{

    private CourseTableModel model;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.course_table);
    }


    @Override
    public void onDataRefresh() {

    }

    @Override
    public void bindAdapter() {
        model = new CourseTableModel();
        adapter = new CourseTableAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addHeader() {
        /**
         *非正式代码，待完善
         */
        WeekSpinnerWrapper spinnerWrapper = new WeekSpinnerWrapper(getContext());
        spinnerWrapper.setIndex(3);
        spinnerWrapper.setOnDayChangedListener(new OnDayChangedListener() {
            @Override
            public void onDayChanged(int day) {
                Toast.makeText(getContext(),day+"",Toast.LENGTH_SHORT).show();
            }
        });
        headerLayout.addView(spinnerWrapper.build());
    }

    @Override
    public void initView() {
    }


}
