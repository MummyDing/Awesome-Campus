package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoLoginBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.SystemUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.NetCallback;

/**
 * Created by yzr on 16/5/14.
 */
public class JxnuGoPeopleDao implements DAO<JxnuGoPeopleModel> {


    private int userId;
    public enum MODE{
        FOLLOWED,FOLLOWERS
    }
    public JxnuGoPeopleDao(){
        userId=-1;
    }
    private MODE mode;
    public JxnuGoPeopleDao(MODE mode,int id){
        this.userId=id;
        this.mode=mode;
    }
    private String TAG="JxnuGoPeopleDao";

    @Override
    public boolean cacheAll(List<JxnuGoPeopleModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    String getRequestUrl(){
       String url=null;
        do{
            if(userId<0)break;
            if(mode==MODE.FOLLOWED)
                url=JxnuGoApi.UserFollowerd+userId;
            else if(mode==MODE.FOLLOWERS)
                url=JxnuGoApi.UserFollowers+userId;
        }while (false);
        Log.d(TAG,url);
        return url;
    }

    public  void loadError(){
        Handler handler=new Handler(Looper.getMainLooper());
        Log.d(TAG,"load jxnugo people error");
        handler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new EventModel<Void>(EVENT.JXNUGO_LOAD_PEOPLELIST_FAILURE));
            }
        });
    }
    @Override
    public void loadFromNet() {
        String url=getRequestUrl();
        if(url==null){
            loadError();
            return;
        }
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(url)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<JxnuGoPeopleListBean>(){
                        @Override
                        public void onSuccess(final JxnuGoPeopleListBean entity, int responseCode, Headers headers) {
                            Log.d(TAG,"load followed success"+entity.getFollowed().length);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                   JxnuGoPeopleModel[]beans=entity.getFollowed();
//                                    for(JxnuGoPeopleModel dao:beans){
//                                        Log.d("JxnuGoPeopleFragment",dao.getUserName());
//                                    }
                                    EventBus.getDefault().post(new EventModel<JxnuGoPeopleListBean>(EVENT.JXNUGO_LOAD_PEOPLELIST_SUCCESS,entity));
                                }
                            });
                        }
                        @Override
                        public void onFailure(String error) {
                          loadError();
                        }
                    });
        } catch (IllegalStateException e) {
            loadError();
            EventBus.getDefault().post(new EventModel<GoodsModel>(EVENT.GOODS_LIST_REFRESH_FAILURE));
        }
    }
}
