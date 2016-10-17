package cn.edu.jxnu.awesome_campus.support.utils.job;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.job.JobDetailBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailUtil {
    private static final String TAG = JobDetailUtil.class.getName();

    private static boolean loadFinished = true;

    public static void loadJobDetail(String detailUrl) {
        if (!loadFinished) {
            return;
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        Log.i(TAG, "开始请求");
        try {
            NetManageUtil.get(detailUrl)
                    .addTag(TAG)
                    .enqueue(new JsonEntityCallback<List<JobDetailBean>>() {
                        @Override
                        public void onFailure(IOException e) {
                            e.printStackTrace();
                            loadFinished = true;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(
                                            EVENT.GET_JOB_DETAIL_FAIL
                                    );
                                }
                            });
                        }

                        @Override
                        public void onSuccess(final List<JobDetailBean> entity, Headers headers) {
                            loadFinished = true;
                            if (entity != null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<JobDetailBean>(
                                                EVENT.GET_JOB_DETAIL_SUCCESS, entity
                                        ));
                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<Void>(
                                                EVENT.NONE_JOB_DETAIL
                                        ));
                                    }
                                });
                            }
                        }
                    });
        } catch (IllegalStateException e) {
            e.printStackTrace();
            loadFinished = true;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<Void>(
                            EVENT.GET_JOB_DETAIL_FAIL
                    ));
                }
            });
        }

    }
}
