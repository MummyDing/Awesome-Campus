package cn.edu.jxnu.awesome_campus.ui.home;

import android.widget.Toast;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.OnDayChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.weekspinner.WeekSpinnerWrapper;

/**
 * Created by MummyDing on 16-1-31.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseTableFragment extends BaseListFragment{

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.course_table);
    }

    @Override
    public void cardViewTransition() {

    }

    @Override
    public void bindAdapter() {

    }

    @Override
    public void addHeader() {
        WeekSpinnerWrapper spinnerWrapper = new WeekSpinnerWrapper(getContext());
        spinnerWrapper.setOnDayChangedListener(new OnDayChangedListener() {
            @Override
            public void onDayChanged(int day) {
                Toast.makeText(getContext(),day+"",Toast.LENGTH_SHORT);
            }
        });
        headerLayout.addView(spinnerWrapper.build());
    }

    @Override
    public void initView() {
    }

    @Override
    protected void onNetworkBtnClick() {

    }
}
