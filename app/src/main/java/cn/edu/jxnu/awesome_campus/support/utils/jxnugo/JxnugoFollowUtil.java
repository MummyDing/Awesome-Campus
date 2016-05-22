package cn.edu.jxnu.awesome_campus.support.utils.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoFollowBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoFollowRetBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUnfolloeBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUnfollowRetBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by yzr on 16/5/16.
 */
public class JxnugoFollowUtil {
    private  String TAG="FollowedingUtil";


    public void  unFollow(int id){
        final Handler handler = new Handler(Looper.getMainLooper());

        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        int userId=spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        Log.d(TAG,userName+password);
        JxnuGoUnfolloeBean bean1=new JxnuGoUnfolloeBean();
        bean1.setUserId(userId);
        bean1.setUnfollowedId(id);
        NetManageUtil.postAuthJson(JxnuGoApi.UnfollowUrl)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .addJsonObject(bean1)
                .enqueue(new JsonEntityCallback<JxnuGoUnfollowRetBean>(){
                    @Override
                    public void onSuccess(JxnuGoUnfollowRetBean entity, Headers headers) {
                        Log.d(TAG,"unfollow"+entity.getUnfollowStatus());
//                        if(entity.getUnfollowStatus().equals("successful"))
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_UNFOLLOW_SUCCESS));

                            }
                        });
                    }
                    @Override
                    public void onFailure(IOException e) {
                        EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_UNFOLLOW_FAILURE));
                    }
                });
    }

    public  void followed(int id){

        final Handler handler = new Handler(Looper.getMainLooper());

        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        int userId=spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        Log.d(TAG,userName+password);
        JxnuGoFollowBean bean=new JxnuGoFollowBean(userId,id);
        NetManageUtil.postAuthJson(JxnuGoApi.FollowUrl)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .addJsonObject(bean)
                .enqueue(new JsonEntityCallback<JxnuGoFollowRetBean>(){
                    @Override
                    public void onSuccess(JxnuGoFollowRetBean entity, Headers headers) {
                        Log.d(TAG,"follow"+entity.getFollowStatus());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_FOLLOW_SUCCESS));

                            }
                        });
                    }
                    @Override
                    public void onFailure(IOException e) {
                       EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_FOLLOW_FAILURE));
                    }
                });
    }
}
