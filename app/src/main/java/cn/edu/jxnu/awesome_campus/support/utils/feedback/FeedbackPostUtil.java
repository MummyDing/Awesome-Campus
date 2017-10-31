package cn.edu.jxnu.awesome_campus.support.utils.feedback;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.api.FeedbackApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.FeedbackBean;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by KevinWu on 16-5-22.
 */
public class FeedbackPostUtil {
    public static final String TAG="FeedbackPostUtil";
    private static Handler handler=new Handler(Looper.getMainLooper());
    public static void postFeedback(FeedbackBean bean){
        NetManageUtil.postJson(FeedbackApi.FeedbackUrl)
                .addJsonObject(bean)
                .addTag(TAG)
                .enqueue(new StringCodeCallback() {
                    @Override
                    public void onSuccess(String result, int responseCode, Headers headers) {
                        if(responseCode==200){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.FEEDBACK_SUCCESS));
                                }
                            });
                        }
                        else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.FEEDBACK_FAILURE));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.FEEDBACK_FAILURE));
                            }
                        });
                    }
                });
    }
}
