package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonEntityCallback;

/**
 * Created by KevinWu on 16-5-11.
 */
public class JxnuGoUserDAO implements DAO<JxnuGoUserModel> {

    private  String TAG="JXNU_GO";
    private int userId;

   public JxnuGoUserDAO(){
        userId=-1;
    }
    public JxnuGoUserDAO(int userId){
       this.userId=userId;
    }
    @Override
    public boolean cacheAll(List<JxnuGoUserModel> list) {
        return false;
    }


    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {
        final Handler handler = new Handler(Looper.getMainLooper());

        if(userId<0){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"load userinfo error");
                    EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_USERINFO_LOAD_USER_FALURE));
                }
            });
//            return;
        }
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        NetManageUtil.getAuth("http://www.jxnugo.com/api/user/"+userId)
                .addUserName(userName)
                .addPassword(password)
                .addTag(TAG)
                .enqueue(new JsonCodeEntityCallback<JxnuGoUserBean>(){

                    @Override
                    public void onSuccess(final JxnuGoUserBean entity, int responseCode, Headers headers) {
                        if(entity!=null){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG,"load user success");
                                    EventBus.getDefault().post(new EventModel<JxnuGoUserBean>(EVENT.JXNUGO_USERINFO_LOAD_USER_SUCCESS,entity));
                                }
                            });
                        }else{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG,"load user failure");
                                    EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_USERINFO_LOAD_USER_FALURE));
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG,"load userinfo error");
                                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_USERINFO_LOAD_USER_FALURE));
                            }
                        });
                    }
                });


    }
}
