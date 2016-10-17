package cn.edu.jxnu.awesome_campus.database.dao.job;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.api.JobApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobBean;
import cn.edu.jxnu.awesome_campus.model.job.JobPage;
import cn.edu.jxnu.awesome_campus.model.job.Post;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cz.msebera.android.httpclient.util.NetUtils;

/**
 * Created by yzr on 16/10/16.
 */

public class JobDao implements DAO<Post> {

    private static final String TAG="Job";

    private Handler handler;

    private int page=1;

    private int type=1;

    public JobDao(int type) {
        this.type = type;
        handler=new Handler(Looper.getMainLooper());
    }

    private void postInMainThread(final EventModel eventModel){
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(eventModel);
            }
        });
    }

    private void refreshError(String error){
        EventModel<String> eventModel=new EventModel(EVENT.JOB_REFRESH_FAILURE,error);
        postInMainThread(eventModel);
    }

    private void refreshSuccess(JobPage jobPage){
        EventModel<Post>eventModel=new EventModel<Post>(EVENT.JOB_REFRESH_SUCCESS,jobPage.getPosts());
        postInMainThread(eventModel);
        this.page=1;
    }
    private void loadError(String error){
        EventModel<String> eventModel=new EventModel(EVENT.JOB_LOAD_FAILURE,error);
        postInMainThread(eventModel);
    }
    private void loadSuccess(JobPage jobPage){
        EventModel<Post>eventModel=new EventModel<Post>(EVENT.JOB_LOAD_SUCCESS,jobPage.getPosts());
        postInMainThread(eventModel);
        this.page+=1;
    }

    public void refresh(){
        Log.e(TAG, "refresh: "+String.format(JobApi.JOB_LIST,type,1) );
        try{
            NetManageUtil.get(String.format(JobApi.JOB_LIST,type,1))
                    .enqueue(new JsonCodeEntityCallback<JobPage>() {
                        @Override
                        public void onSuccess(JobPage entity, int responseCode, Headers headers) {
                            if(entity==null||entity.getPosts()==null||entity.getPosts().isEmpty()){
                                refreshError("empty");
                            }else{
                                refreshSuccess(entity);
                            }
                        }
                        @Override
                        public void onFailure(String error) {
                            refreshError(error);
                        }
                    });
        }catch (Exception e){
            refreshError(e.getMessage());
        }
    }

    public void loadMore(){
        try{
            Log.e(TAG, "loadMore: " +String.format(JobApi.JOB_LIST,type,page+1));
            NetManageUtil.get(String.format(JobApi.JOB_LIST,type,page+1))
                    .enqueue(new JsonCodeEntityCallback<JobPage>() {
                        @Override
                        public void onSuccess(JobPage entity, int responseCode, Headers headers) {
                            if(entity==null||entity.getPosts()==null||entity.getPosts().isEmpty()){
                                loadError("empty");
                            }else{
                               loadSuccess(entity);
                            }
                        }
                        @Override
                        public void onFailure(String error) {
                            loadError(error);
                        }
                    });
        }catch (Exception e){
            loadError(e.getMessage());
        }
    }

    @Override
    public boolean cacheAll(List<Post> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }

}
