package cn.edu.jxnu.awesome_campus.ui.education;

import android.util.Log;
import android.view.View;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

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
    private List<CourseScoreModel> courseScoreList;
    private TermSpinnerWrapper spinnerWrapper;
    @Override
    public String getTitle() {
        return InitApp.AppContext.getString(R.string.Score);
    }


    @Override
    public void onDataRefresh() {
        super.onDataRefresh();
        model.loadFromNet();
    }

    @Override
    public void bindAdapter() {
        model = new CourseScoreModel();
        adapter = new CourseScoreAdapter(getActivity(),model);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void addHeader() {
        if(EducationLoginUtil.isLogin() && spinnerWrapper == null){
            spinnerCard.setVisibility(View.VISIBLE);
            spinnerWrapper = new TermSpinnerWrapper();
            spinnerWrapper.setOnTermChangedListener(new OnTermChangedListener() {
                @Override
                public void onTermChanged(int termID, String termName) {
                    adapter.newList(getCourseScoreByTerm(termID));
                }
            });
            spinnerWrapper.build((MaterialSpinner) parentView.findViewById(R.id.spinner));
        }
    }

    @Override
    public void initView() {
        setOnLineLayout(EducationLoginUtil.isLogin());
        if (EducationLoginUtil.isLogin()){
            model.loadFromCache();
        }
    }


    @Override
    public void onEventComing(EventModel eventModel) {
        switch (eventModel.getEventCode()){
            case EVENT.COURSE_SCORE_LOAD_CACHE_SUCCESS:
            case EVENT.COURSE_SCORE_REFRESH_SUCCESS:
                courseScoreList = eventModel.getDataList();
                if(spinnerWrapper == null){
                    addHeader();
                }else {
                    spinnerWrapper.updateAttachList();
                }
                adapter.newList(getCourseScoreByTerm(spinnerWrapper.getIndex()));
                hideLoading();
                break;
            case EVENT.COURSE_SCORE_REFRESH_FAILURE:
                hideLoading();
                break;
            case EVENT.COURSE_SCORE_LOAD_CACHE_FAILURE:
                onDataRefresh();
                break;
        }
    }


    private List<CourseScoreModel> getCourseScoreByTerm(int term){
        return getCourseScoreByTerm(TermUtil.getTermList().get(term));
    }
    private List<CourseScoreModel> getCourseScoreByTerm(String term){
        if( courseScoreList == null){
            return null;
        }
        List<CourseScoreModel> tempList = new ArrayList<>();
        for(CourseScoreModel model : courseScoreList){
            if(model.getTerm().equals(term)){
                tempList.add(model);
            }
        }
        return tempList;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("poooooo","dfdfd");
    }
}
