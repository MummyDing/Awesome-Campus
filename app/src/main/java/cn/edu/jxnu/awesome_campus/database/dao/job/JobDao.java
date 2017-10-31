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
import cn.edu.jxnu.awesome_campus.ui.job.JobContact;
import cz.msebera.android.httpclient.util.NetUtils;

/**
 * Created by yzr on 16/10/16.
 */

public class JobDao implements DAO<Post>,JobContact.Presenter {



    private static final String TAG="Job";

    private Handler handler;

    private int page=1;

    private int type=1;

    JobContact.View view;

    public JobDao(int type,JobContact.View view) {
        this.type = type;
        this.view=view;
        handler=new Handler(Looper.getMainLooper());
    }


    private void refreshError(final String error){
       handler.post(new Runnable() {
           @Override
           public void run() {
               view.onError(error);
           }
       });
    }

    private void refreshSuccess(final List<Post>list){
        page=1;
      handler.post(new Runnable() {
          @Override
          public void run() {
              view.onRefreshJobData(list);
          }
      });
    }
    private void loadError(final String error){
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onError(error);
            }
        });
    }
    private void loadSuccess(final List<Post>list){
        page+=1;
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onMoreJobData(list);
            }
        });
    }


    @Override
    public void moreJobData() {
        try{
            NetManageUtil.get(String.format(JobApi.JOB_LIST,type,page+1))
                    .enqueue(new JsonCodeEntityCallback<JobPage>() {
                        @Override
                        public void onSuccess(JobPage entity, int responseCode, Headers headers) {
                            if(entity==null||entity.getPosts()==null||entity.getPosts().isEmpty()){
                               loadError("empty");
                            }else{
                                loadSuccess(entity.getPosts());
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
    public void refreshJobData() {

        try{
            NetManageUtil.get(String.format(JobApi.JOB_LIST,type,1))
                    .enqueue(new JsonCodeEntityCallback<JobPage>() {
                        @Override
                        public void onSuccess(JobPage entity, int responseCode, Headers headers) {
                            if(entity==null||entity.getPosts()==null||entity.getPosts().isEmpty()){
                               refreshError("empty");
                            }else{
                               refreshSuccess(entity.getPosts());
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
