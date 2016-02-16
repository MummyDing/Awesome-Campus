package cn.edu.jxnu.awesome_campus.ui.education;

import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.adapter.education.CourseScoreAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TermUtil;
import cn.edu.jxnu.awesome_campus.support.utils.login.EducationLoginUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseListFragment;
import cn.edu.jxnu.awesome_campus.view.widget.termspinner.OnTermChangedListener;
import cn.edu.jxnu.awesome_campus.view.widget.termspinner.TermSpinnerWrapper;

/**
 * Created by MummyDing on 16-2-1.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CourseScoreFragment extends BaseListFragment {

    private CourseScoreModel model;

    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.source);
    }


    @Override
    public void onDataRefresh() {
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new CourseScoreModel();
        adapter = new CourseScoreAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);
        model.loadFromNet();
        displayLoading();
    }

    @Override
    public void addHeader() {
        if(EducationLoginUtil.isLogin()){
            TermSpinnerWrapper spinnerWrapper = new TermSpinnerWrapper();
            spinnerWrapper.setOnTermChangedListener(new OnTermChangedListener() {
                @Override
                public void onTermChanged(int term) {
                    Toast.makeText(getContext(),"学期"+term,Toast.LENGTH_SHORT).show();
                }
            });
            spinnerWrapper.build((NiceSpinner) parentView.findViewById(R.id.spinner));
            setOnLineLayout(true);
        }else {
            setOnLineLayout(false);
        }
    }

    @Override
    public void initView() {

    }


    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.COURSE_SCORE_REFRESH_SUCCESS:
                addHeader();
                hideLoading();
                // 这里还需要处理数据
                break;
            case EVENT.COURSE_SCORE_REFRESH_FAILURE:
                hideLoading();
                break;
        }
    }
}
