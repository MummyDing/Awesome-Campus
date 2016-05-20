package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.squareup.okhttp.Headers;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.api.JxnuGoApi;
import cn.edu.jxnu.awesome_campus.database.dao.DAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCollectionListBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.UserCollectionModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by KevinWu on 16-5-20.
 */
public class UserCollectionDAO implements DAO<UserCollectionModel> {
    public static final String TAG="UserCollectionDAO";
    @Override
    public boolean cacheAll(List<UserCollectionModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {
        this.loadFromNet();
    }

    @Override
    public void loadFromNet() {
        SPUtil spu = new SPUtil(InitApp.AppContext);
        int userId=spu.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.BaseUserCollectionPostUrl+userId)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<UserCollectionListBean>() {
                        @Override
                        public void onSuccess(UserCollectionListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<UserCollectionListBean> list = Arrays.asList(entity);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCollectionListBean>(EVENT.USERCOLLECT_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<UserCollectionListBean>(EVENT.USERCOLLECT_REFRESH_FAILURE));
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
                                }
                            });
                        }
                    });
        } catch (IllegalStateException e) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
                }
            });
        }
    }
}
