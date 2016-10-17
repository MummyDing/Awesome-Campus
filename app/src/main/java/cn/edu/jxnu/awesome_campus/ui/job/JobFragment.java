package cn.edu.jxnu.awesome_campus.ui.job;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.job.JobDao;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobBean;
import cn.edu.jxnu.awesome_campus.model.job.Post;
import cn.edu.jxnu.awesome_campus.support.adapter.job.JobAdapter;
import cn.edu.jxnu.awesome_campus.support.recycle.BaseSwipeAdapter;
import cn.edu.jxnu.awesome_campus.support.recycle.BaseSwipeFragment;
import cn.edu.jxnu.awesome_campus.support.recycle.NormalSwipeFragment;
import cn.finalteam.toolsfinal.logger.Logger;

/**
 * Created by yzr on 16/10/16.
 */

public class JobFragment extends NormalSwipeFragment<Post> {

    private int type;

    public static JobFragment newInstance(int type){
        JobFragment fragment=new JobFragment();
        fragment.type=type;
        return fragment;
    }

    @Override
    public String getTitle() {
        if(type==1){
            return InitApp.AppContext.getResources().getString(R.string.job_title_school);
        }else{
            return InitApp.AppContext.getResources().getString(R.string.job_title_big);
        }
    }

    @Override
    public void onEventComing(EventModel eventModel) {
        Log.e(TAG, "onEvent: ");
        Log.e(TAG, "onEventComing: "+eventModel.getEventCode() );
        switch (eventModel.getEventCode()){
            case EVENT.JOB_LOAD_SUCCESS:
                onLoadSuccess(eventModel);
                break;
            case EVENT.JOB_REFRESH_SUCCESS:
                onRefreshSuccess(eventModel);
                break;
            case EVENT.JOB_LOAD_FAILURE:
            case EVENT.JOB_REFRESH_FAILURE:
                onRefreshCompleted();
                break;
        }
    }

    private static String TAG="job";
    private JobDao dao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dao=new JobDao(1);
        View view= normalView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public BaseSwipeAdapter<Post> createItemAdapter(List<Post> list) {
        return new JobAdapter(list);
    }

    @Override
    public void loadMore() {
        Log.e(TAG, "loadMore: " );
        dao.loadMore();
    }

    @Override
    public void refresh() {
        dao.refresh();
        Log.e(TAG, "refresh: " );
    }

    private void onLoadSuccess(EventModel eventModel){
        Log.e(TAG, "onLoadSuccess: ");
        onMoreData((List<Post>)eventModel.getDataList());
        List<Post>x=eventModel.getDataList();
        for(int i=0;i<x.size();i++){
            Log.e(TAG, "onLoadSuccess: "+x.get(i).getId()+x.get(i).getUserId());
        }
    }

    private void onRefreshSuccess(EventModel eventModel){
        Log.e(TAG, "onRefreshSuccess: " );
        onRefreshData((List<Post>)eventModel.getDataList());
        List<Post>x=eventModel.getDataList();
        for(int i=0;i<x.size();i++){
            Log.e(TAG, "onLoadSuccess: "+x.get(i).getId()+x.get(i).getUserId());
        }
    }
}
