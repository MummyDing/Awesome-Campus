package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectRTBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.CollectStatusBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCodeCallback;

/**
 * Created by KevinWu on 16-5-15.
 */
public class UploadCollectingStatusUtil {
    public static final String TAG="UploadCollectingStatus.";

    public static void onUploadJson(boolean hasCollect,CollectBean pbean){
        final Handler handler=new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        /*未收藏*/
        if(hasCollect){
            NetManageUtil.postAuthJson(JxnuGoApi.BaseCollectUrl)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .addJsonObject(pbean)
                    .enqueue(new JsonCodeEntityCallback<CollectRTBean>() {

                        @Override
                        public void onSuccess(CollectRTBean entity, int responseCode, Headers headers) {
                            if(responseCode==200){
                                Log.d(TAG,entity.getCollectStatus());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<CollectRTBean>(EVENT.POST_COLLECT_SUCCESS));
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<CollectRTBean>(EVENT.POST_COLLECT_FAILURE));
                                }
                            });
                        }
                    });
//            .enqueue(new StringCodeCallback() {
//                @Override
//                public void onSuccess(String result, int responseCode, Headers headers) {
//                    Log.d(TAG,"-"+responseCode+" "+result);
//                }
//
//                @Override
//                public void onFailure(String error) {
//
//                }
//            });
            /*已收藏*/
        }else{
            NetManageUtil.postAuthJson(JxnuGoApi.BaseUnCollectUrl)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .addJsonObject(pbean)
                    .enqueue(new JsonCodeEntityCallback<CollectRTBean>() {

                        @Override
                        public void onSuccess(CollectRTBean entity, int responseCode, Headers headers) {
                            if(responseCode==200){
                                Log.d(TAG,entity.getUncollectStatus());

                            }
                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    });
        }
    }

    public static void getStatusJson(CollectBean pbean){
        final Handler handler=new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
            NetManageUtil.postAuthJson(JxnuGoApi.JudgeCollect)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .addJsonObject(pbean)
                    .enqueue(new JsonCodeEntityCallback<CollectStatusBean>() {

                        @Override
                        public void onSuccess(CollectStatusBean entity, int responseCode, Headers headers) {
                            if(responseCode==200){
                                if(entity.getCollectInfo()==0){
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new EventModel<CollectStatusBean>(EVENT.JUDGE_COLLECT_FALSE));
                                        }
                                    });
                                }else if(entity.getCollectInfo()==1){
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            EventBus.getDefault().post(new EventModel<CollectStatusBean>(EVENT.JUDGE_COLLECT_TRUE));
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
                                    EventBus.getDefault().post(new EventModel<CollectStatusBean>(EVENT.JUDGE_COLLECT_FALSE));
                                }
                            });
                        }
                    });
    }
}
