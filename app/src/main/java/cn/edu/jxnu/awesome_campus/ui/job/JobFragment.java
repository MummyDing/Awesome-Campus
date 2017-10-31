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

public class JobFragment extends NormalSwipeFragment<Post> implements JobContact.View {

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


    private static String TAG="job";
    private JobDao dao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dao=new JobDao(type==1?1:2,this);
        View view= normalView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public BaseSwipeAdapter<Post> createItemAdapter(List<Post> list) {
        return new JobAdapter(list);
    }

    @Override
    public void loadMore() {
        dao.moreJobData();
        Log.e(TAG, "loadMore: " );

    }

    @Override
    public void refresh() {
        dao.refreshJobData();
        Log.e(TAG, "refresh: " );
    }

    @Override
    public void onEventComing(EventModel eventModel) {

    }

    @Override
    public void onMoreJobData(List<Post> list) {
        onMoreData(list);
    }

    @Override
    public void onRefreshJobData(List<Post> list) {
        onRefreshData(list);
    }

    @Override
    public void onError(String err) {
        onRefreshCompleted();
    }
}
