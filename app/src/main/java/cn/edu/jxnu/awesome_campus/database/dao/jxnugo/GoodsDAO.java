package cn.edu.jxnu.awesome_campus.database.dao.jxnugo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
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
import cn.edu.jxnu.awesome_campus.model.jxnugo.GoodsModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.JsonCodeEntityCallback;

/**
 * Created by KevinWu on 16-5-11.
 */
public class GoodsDAO implements DAO<GoodsModel> {
    private static final String TAG = "GoodsDAO";

    @Override
    public boolean cacheAll(List<GoodsModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {
        loadFromNet();
    }

    @Override
    public void loadFromNet() {
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String userName = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.USERNAME);
        String password = spu.getStringSP(JxnuGoStaticKey.SP_FILE_NAME, JxnuGoStaticKey.PASSWORD);
        final Handler handler = new Handler(Looper.getMainLooper());
        try {
            NetManageUtil.getAuth(JxnuGoApi.AllPostUrl)
                    .addUserName(userName)
                    .addPassword(password)
                    .addTag(TAG)
                    .enqueue(new JsonCodeEntityCallback<GoodsListBean>() {
                        @Override
                        public void onSuccess(GoodsListBean entity, int responseCode, Headers headers) {
                            if (entity != null) {
                                final List<GoodsListBean> list = Arrays.asList(entity);
//                                Log.d(TAG, "取得的条数" + entity.getCount());
//                                Log.d(TAG, "下一页" + entity.getNext());
//                                Log.d(TAG, "实际条数" + entity.getPosts().length);
//                                Log.d(TAG, "获取到的第一条信息" + entity.getPosts()[0].getBody());
//                                Log.d(TAG, "商品模型列表大小" + list.size());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_SUCCESS, list));

                                    }
                                });
                            } else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        EventBus.getDefault().post(new EventModel<GoodsListBean>(EVENT.GOODS_LIST_REFRESH_FAILURE));
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
