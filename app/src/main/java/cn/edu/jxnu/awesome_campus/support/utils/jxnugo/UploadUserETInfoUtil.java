package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectStatusBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.FollowerJudgeBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.FollowerJudgeInfoBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoFollowBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UpdateInfoBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UpdateUserInfoRTBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by KevinWu on 16-5-19.
 */
public class UploadUserETInfoUtil {
    public static final String TAG="UploadUserETInfoUtil";

    private static String getUserName(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
    }
    private static String getPassword(Context context){
        SPUtil sp=new SPUtil(context);
        return sp.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
    }
    public static void uploadData(Context context, UpdateInfoBean bean){
        final Handler handler=new Handler(Looper.getMainLooper());
        NetManageUtil.postAuthJson(JxnuGoApi.UpdateUserInfo)
                .addJsonObject(bean)
                .addTag(TAG)
                .addUserName(getUserName(context))
                .addPassword(getPassword(context))
                .enqueue(new JsonCodeEntityCallback<UpdateUserInfoRTBean>() {
                    @Override
                    public void onSuccess(UpdateUserInfoRTBean entity, int responseCode, Headers headers) {
                        if(responseCode==200){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<UpdateUserInfoRTBean>(EVENT.UPDATE_USER_INFO_SUCCESS));
                                }
                            });
                        }else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<UpdateUserInfoRTBean>(EVENT.UPDATE_USER_INFO_FAILURE));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<UpdateUserInfoRTBean>(EVENT.UPDATE_USER_INFO_FAILURE));
                            }
                        });
                    }
                });

    }
    public static void getStatusJson(FollowerJudgeBean pbean){
        final Handler handler=new Handler(Looper.getMainLooper());
        NetManageUtil.postAuthJson(JxnuGoApi.JudgeFollow)
                .addUserName(getUserName(InitApp.AppContext))
                .addPassword(getPassword(InitApp.AppContext))
                .addTag(TAG)
                .addJsonObject(pbean)
                .enqueue(new JsonCodeEntityCallback<FollowerJudgeInfoBean>() {

                    @Override
                    public void onSuccess(FollowerJudgeInfoBean entity, int responseCode, Headers headers) {
                        if(responseCode==200){
                            if(entity.getJudgeInfo()==0){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<FollowerJudgeInfoBean>(EVENT.JUDGE_FOLLOW_FALSE));
                                    }
                                });
                            }else if(entity.getJudgeInfo()==1){
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<FollowerJudgeInfoBean>(EVENT.JUDGE_FOLLOW_TRUE));
                                    }
                                });
                            }
                        }
                    }
                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<FollowerJudgeInfoBean>(EVENT.JUDGE_FOLLOW_FALSE));
                            }
                        });
                    }
                });
    }
}
